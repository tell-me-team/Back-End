package com.tellme.tellme.domain.user.entity;

import com.tellme.tellme.domain.BaseEntity;
import jakarta.persistence.*;

@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 20)
    private String email;
    @Column(length = 200)
    private String password;
    @Column(length = 30)
    private String nickname;
    @Column(length = 20)
    private String social_type;

}
