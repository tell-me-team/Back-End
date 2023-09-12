package com.tellme.tellme.domains.user.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Getter
public class User implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }


    /**
     * security 에서 사용하는 회원 구분 id
     */
    @JsonProperty(access = WRITE_ONLY)
    @Override
    public String getUsername() {
        return Integer.toString(this.id);
    }

    /**
     * 계정이 만료되었는지 체크하는 로직
     * 사용하지 않아 true 값 return
     */
    @JsonProperty(access = WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정이 잠겼는지 체크하는 로직
     * 사용하지 않아 true 값 return
     */
    @JsonProperty(access = WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 계정의 패스워드가 만료되었는지 체크하는 로직
     * 사용하지 않아 true 값 return
     */
    @JsonProperty(access = WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정이 사용가능한지 체크하는 로직
     * 사용하지 않아 true 값 return
     */
    @JsonProperty(access = WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
