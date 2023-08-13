package com.tellme.tellme.domain.survey.entity;

import com.tellme.tellme.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "survey_answer")
public class SurveyAnswer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = SurveyCompletion.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_completion_id")
    private SurveyCompletion surveyCompletion;

    @ManyToOne(targetEntity = Question.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(length = 1)
    private char answer;

    @Builder
    public SurveyAnswer(SurveyCompletion surveyCompletion, Question question, char answer) {
        this.surveyCompletion = surveyCompletion;
        this.question = question;
        this.answer = answer;
    }

    public void updateSurveyAnswer(char answer) {
        this.answer = answer;
    }
}
