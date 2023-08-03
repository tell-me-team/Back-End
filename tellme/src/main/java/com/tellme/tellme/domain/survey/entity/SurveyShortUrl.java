package com.tellme.tellme.domain.survey.entity;

import com.tellme.tellme.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "survey_shrot_url")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyShortUrl extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "survey_id")
    private int surveyId;
    @Column(name = "user_id")
    private long userId;
    private String url;

    @Builder
    public SurveyShortUrl(int surveyId, long userId, String url) {
        this.surveyId = surveyId;
        this.userId = userId;
        this.url = url;
    }

}
