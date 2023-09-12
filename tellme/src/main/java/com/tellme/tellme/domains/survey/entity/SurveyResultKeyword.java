package com.tellme.tellme.domains.survey.entity;

import com.tellme.tellme.domains.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "survey_result_keyword")
public class SurveyResultKeyword extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String title;

    @ManyToOne(targetEntity = SurveyResult.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_result_id")
    private SurveyResult surveyResult;
}
