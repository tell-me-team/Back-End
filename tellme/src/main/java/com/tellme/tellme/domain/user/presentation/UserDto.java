package com.tellme.tellme.domain.user.presentation;


import lombok.*;

public class UserDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class UserInfo{
        private int userId;
        private String profileImage;
    }

}
