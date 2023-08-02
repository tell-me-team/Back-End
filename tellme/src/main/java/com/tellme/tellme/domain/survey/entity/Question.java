package com.tellme.tellme.domain.survey.entity;

import com.tellme.tellme.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int orderNumber;
    @Column(length = 500)
    private String question;
    @Column(length = 300)
    private String answerA;
    @Column(length = 300)
    private String answerB;
}
