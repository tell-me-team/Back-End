package com.tellme.tellme.domains.user.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tellme.tellme.domains.BaseEntity;
import com.tellme.tellme.domains.user.domain.User;
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
@Builder
@NoArgsConstructor
@Table(name = "USER")
public class UserEntity extends BaseEntity {

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
    @CollectionTable(name = "USER_ROLES")
    private List<String> roles = new ArrayList<>();


    @Builder
    public UserEntity(int id, String email, String password, String nickname, String picture, String socialType, List<String> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.picture = picture;
        this.socialType = socialType;
        this.roles = roles;
    }

    public static UserEntity from(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.id = user.getId();
        userEntity.email = user.getEmail();
        userEntity.password = user.getPassword();
        userEntity.nickname = user.getNickname();
        userEntity.picture = user.getPicture();
        userEntity.socialType = user.getSocialType();
        userEntity.roles = user.getRoles();
        return userEntity;
    }

    public User toModel() {
        return User.builder()
                .id(id)
                .email(email)
                .password(password)
                .nickname(nickname)
                .picture(picture)
                .socialType(socialType)
                .roles(roles)
                .build();
    }




}
