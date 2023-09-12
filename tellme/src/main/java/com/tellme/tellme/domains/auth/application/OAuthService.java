package com.tellme.tellme.domains.auth.application;

import com.tellme.tellme.common.auth.JwtTokenProvider;
import com.tellme.tellme.common.enums.SocialLoginType;
import com.tellme.tellme.domains.user.application.UserServiceImpl;
import com.tellme.tellme.domains.user.infrastructure.UserEntity;
import com.tellme.tellme.domains.user.infrastructure.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.tellme.tellme.domains.auth.presentation.AuthDto.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final KakaoOauth kakaoOauth;
    private final UserServiceImpl userServiceImpl;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserJpaRepository userJpaRepository;


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
                if(userServiceImpl.checkByEmail(kakaoUser.getKakao_account().getEmail())) {

                    UserEntity userEntity = userServiceImpl.getByEmail(kakaoUser.getKakao_account().getEmail());
                    String accessToken = jwtTokenProvider.createAccessToken(userEntity.getId(), List.of("ROLE_USER"));
                    String refreshToken = jwtTokenProvider.createRefreshToken(userEntity.getId(), List.of("ROLE_USER"));

                    GetSocialOAuthRes getSocialOAuthRes = new GetSocialOAuthRes(userEntity.getId(), userEntity.getPicture(), accessToken, refreshToken);
                    return getSocialOAuthRes;

                // 회원가입
                }else {

                    UserEntity userEntity = userJpaRepository.save(kakaoUser.toEntity());
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
