package com.tellme.tellme.domain.survey.entity;

import com.tellme.tellme.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "question")
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "order_number")
    private int orderNumber;
    @Column(length = 500)
    private String question;
    @Column(length = 300, name = "answer_a")
    private String answerA;
    @Column(length = 300, name = "answer_b")
    private String answerB;
    @Column(length = 300, name = "answer_a_result")
    private String answerAResult;
    @Column(length = 300, name = "answer_b_result")
    private String answerBResult;
}
