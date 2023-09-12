package com.tellme.tellme.domains.user.domain;


import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class User {

    private int id;
    private String email;
    private String password;
    private String nickname;
    private String picture;
    private String socialType;
    private List<String> roles = new ArrayList<>();

    @Builder
    public User(int id, String email, String password, String nickname, String picture, String socialType, List<String> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.picture = picture;
        this.socialType = socialType;
        this.roles = roles;
    }
}
