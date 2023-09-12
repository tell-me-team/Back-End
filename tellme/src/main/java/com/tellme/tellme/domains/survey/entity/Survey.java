package com.tellme.tellme.domains.survey.entity;

import com.tellme.tellme.domains.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "survey")
public class Survey extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 100)
    private String title;
    @Column(length = 2000)
    private String content;
    @Column(length = 2000)
    private String image;

    @OneToMany(mappedBy = "survey")
    private List<SurveyQuestion> surveyQuestions = new ArrayList<>();

}
