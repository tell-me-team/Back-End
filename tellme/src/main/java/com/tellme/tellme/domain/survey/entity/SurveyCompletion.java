package com.tellme.tellme.domain.survey.entity;

import com.tellme.tellme.domain.BaseEntity;
import com.tellme.tellme.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyCompletion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Survey.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user_id")
    private User user;

    @Column(name = "uuid")
    private String uuid;

    @Builder
    public SurveyCompletion(Survey survey, User user, String uuid) {
        this.survey = survey;
        this.user = user;
        this.uuid = uuid;
    }
}
