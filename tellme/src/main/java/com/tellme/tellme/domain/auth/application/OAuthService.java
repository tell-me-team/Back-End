package com.tellme.tellme.domain.auth.application;

import com.tellme.tellme.common.auth.JwtTokenProvider;
import com.tellme.tellme.common.enums.SocialLoginType;
import com.tellme.tellme.domain.user.application.UserService;
import com.tellme.tellme.domain.user.entity.*;
import com.tellme.tellme.domain.user.infrastructure.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.tellme.tellme.domain.auth.presentation.AuthDto.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final KakaoOauth kakaoOauth;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserEntityRepository userEntityRepository;


    public GetSocialOAuthRes oAuthLoginOrJoin(SocialLoginType socialLoginType, String code) throws IOException {

        switch (socialLoginType) {
            case KAKAO: {

                // 엑세스 토큰 GET
                ResponseEntity<String> accessTokenResponse = kakaoOauth.requestAccessToken(code);
                log.info(">> 카카오 AccessToken : {}", accessTokenResponse);
                KakaoOAuthToken oAuthToken = kakaoOauth.getAccessToken(accessTokenResponse);

                // 유저 정보 GET
                ResponseEntity<String> userInfoResponse = kakaoOauth.requestUserInfo(oAuthToken);
                log.info(">> 카카오 UserInfo 가공 전 : {}", userInfoResponse);
                KakaoUser kakaoUser = kakaoOauth.getUserInfo(userInfoResponse);
                log.info(">> 카카오 UserInfo 가공 후: {}", kakaoUser);

                // 로그인
                if(userService.checkByEmail(kakaoUser.getKakao_account().getEmail())) {

                    UserEntity userEntity = userService.getByEmail(kakaoUser.getKakao_account().getEmail());
                    String accessToken = jwtTokenProvider.createAccessToken(userEntity.getId(), List.of("ROLE_USER"));
                    String refreshToken = jwtTokenProvider.createRefreshToken(userEntity.getId(), List.of("ROLE_USER"));

                    GetSocialOAuthRes getSocialOAuthRes = new GetSocialOAuthRes(userEntity.getId(), userEntity.getPicture(), accessToken, refreshToken);
                    return getSocialOAuthRes;

                // 회원가입
                }else {

                    UserEntity userEntity = userEntityRepository.save(kakaoUser.toEntity());
                    String accessToken = jwtTokenProvider.createAccessToken(userEntity.getId(), List.of("ROLE_USER"));
                    String refreshToken = jwtTokenProvider.createRefreshToken(userEntity.getId(), List.of("ROLE_USER"));

                    GetSocialOAuthRes getSocialOAuthRes = new GetSocialOAuthRes(userEntity.getId(), userEntity.getPicture(), accessToken, refreshToken);
                    return getSocialOAuthRes;
                }

            }
            default: {
                throw new IOException();
            }

        }
    }


}
