package com.tellme.tellme.domain.survey.entity;

import com.tellme.tellme.domain.BaseEntity;
import com.tellme.tellme.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "survey_completion")
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

    @Column(name = "unique_id")
    private String uniqueId;

    @OneToMany(mappedBy = "surveyCompletion")
    private List<SurveyAnswer> surveyAnswers = new ArrayList<>();

    @Builder
    public SurveyCompletion(Survey survey, User user, String uniqueId) {
        this.survey = survey;
        this.user = user;
        this.uniqueId = uniqueId;
    }
}
