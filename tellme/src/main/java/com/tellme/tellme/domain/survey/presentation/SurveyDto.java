package com.tellme.tellme.domain.survey.presentation;

import com.tellme.tellme.domain.survey.entity.Question;
import com.tellme.tellme.domain.survey.entity.Survey;
import com.tellme.tellme.domain.survey.entity.SurveyAnswer;
import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import com.tellme.tellme.domain.user.entity.User;
import lombok.*;

import java.util.List;

public class SurveyDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class Answer {
        private int surveyId;
        private String uuid;
        private List<AnswerContent> answerContentList;

        public SurveyCompletion toSurveyCompletion(Survey survey, User user) {
            return SurveyCompletion.builder()
                    .survey(survey)
                    .user(user)
                    .uuid(uuid)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class AnswerContent {
        private int question;
        private char answer;

        public SurveyAnswer toSurveyAnswer(SurveyCompletion surveyCompletion, Question question) {
            return SurveyAnswer.builder()
                    .surveyCompletion(surveyCompletion)
                    .question(question)
                    .answer(answer)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class SurveyCompletionWithAnswers {
        private String question;
        private Character answerToMe;
        private String answerToOther;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class SurveyInfo{
        private long userId;
        private int surveyId;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class QuestionInfo{
        private String question;
        private String answerA;
        private String answerB;
    }
}

