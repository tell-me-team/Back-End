package com.tellme.tellme.domains.auth.presentation;

import com.tellme.tellme.common.enums.SocialLoginType;
import com.tellme.tellme.common.enums.UserRole;
import com.tellme.tellme.domains.user.infrastructure.UserEntity;
import lombok.*;

import java.util.List;

public class AuthDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class GetSocialOAuthRes {

        private int userId;
        private String userPicture;
        private String accessToken;
        private String refreshToken;


    }

    //구글에 일회성 코드를 다시 보내 받아올 액세스 토큰을 포함한 JSON 문자열을 담을 클래스
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class KakaoOAuthToken {
        private String access_token;
        private String token_type;
        private String refresh_token;
        private int expires_in;
        private String scope;
        private String refresh_token_expires_in;

    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class KakaoUser {

        public properties properties;
        public kakao_account kakao_account;

        public UserEntity toEntity() {
            return UserEntity.builder()
                    .email(this.getKakao_account().getEmail())
                    .password("NONE")
                    .nickname(this.getProperties().getNickname())
                    .picture(this.getProperties().getProfile_image())
                    .socialType(SocialLoginType.KAKAO.toString())
                    .roles(List.of("ROLE_" + UserRole.USER.toString())) // TODO. ENUM으로 관리
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class kakao_account {

        public String email;
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class properties {
        public String nickname;
        public String profile_image;
        public String thumbnail_image;

    }

}
