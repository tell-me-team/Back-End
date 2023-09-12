package com.tellme.tellme.domains.user.presentation;


import lombok.*;

import java.util.List;

public class UserDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class UserInfo{
        private int userId;
        private String profileImage;
        private List<Integer> MyCompleteSurveyList;
    }

}
