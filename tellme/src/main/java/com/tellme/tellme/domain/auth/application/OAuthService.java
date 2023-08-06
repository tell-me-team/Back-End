package com.tellme.tellme.domain.auth.application;

import com.tellme.tellme.common.auth.JwtTokenProvider;
import com.tellme.tellme.common.enums.SocialLoginType;
import com.tellme.tellme.domain.user.application.UserService;
import com.tellme.tellme.domain.user.entity.*;
import com.tellme.tellme.domain.user.persistence.UserRepository;
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
    private final UserRepository userRepository;


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
                if(userService.checkUserByEmail(kakaoUser.getKakao_account().getEmail())) {

                    User user = userService.getUserByEmail(kakaoUser.getKakao_account().getEmail());
                    String accessToken = jwtTokenProvider.createAccessToken(user.getId(), List.of("ROLE_USER"));
                    String refreshToken = jwtTokenProvider.createRefreshToken(user.getId(), List.of("ROLE_USER"));

                    GetSocialOAuthRes getSocialOAuthRes = new GetSocialOAuthRes(user.getId(), accessToken, refreshToken);
                    return getSocialOAuthRes;

                // 회원가입
                }else {

                    User user = userRepository.save(kakaoUser.toEntity());
                    String accessToken = jwtTokenProvider.createAccessToken(user.getId(), List.of("ROLE_USER"));
                    String refreshToken = jwtTokenProvider.createRefreshToken(user.getId(), List.of("ROLE_USER"));

                    GetSocialOAuthRes getSocialOAuthRes = new GetSocialOAuthRes(user.getId(), accessToken, refreshToken);
                    return getSocialOAuthRes;
                }

            }
            default: {
                throw new IOException();
            }

        }
    }


}
