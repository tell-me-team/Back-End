package com.tellme.tellme.domain.user.presentation;

import com.tellme.tellme.domain.user.entity.User;
import lombok.*;

public class UserDto {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class GetUserRes {
        private Long id;
        private String email;
        private String nickname;

        public GetUserRes(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.nickname = user.getNickname();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class PostUserRes {
        private Long id;
        private String jwt;

    }

}
