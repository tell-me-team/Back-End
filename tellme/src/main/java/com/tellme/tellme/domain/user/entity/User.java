package com.tellme.tellme.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tellme.tellme.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.*;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String email;

    @JsonProperty(access = WRITE_ONLY) // Json 결과로 출력하지 않을 데이터에 대해 해당 어노테이션 설정 값 추가
    @Column(length = 200)
    private String password;

    @Column(length = 30)
    private String nickname;

    @Column(length = 2000)
    private String picture;

    @Column(length = 20)
    private String socialType;


    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Builder
    public User(String email, String password, String nickname, String picture, String socialType, List<String> roles) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.picture = picture;
        this.socialType = socialType;
        this.roles = roles;
    }

    /**
     * security 에서 사용하는 회원 구분 id
     */
    @JsonProperty(access = WRITE_ONLY)
    @Override
    public String getUsername() {
        // TODO. USER ID로 변경
        return this.email;
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
