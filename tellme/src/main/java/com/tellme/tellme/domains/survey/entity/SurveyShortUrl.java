package com.tellme.tellme.domains.survey.entity;

import com.tellme.tellme.domains.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "survey_short_url")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyShortUrl extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "survey_id")
    private int surveyId;
    @Column(name = "user_id")
    private int userId;
    private String url;

    @Builder
    public SurveyShortUrl(int surveyId, int userId, String url) {
        this.surveyId = surveyId;
        this.userId = userId;
        this.url = url;
    }

}
