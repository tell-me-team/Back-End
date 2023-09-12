package com.tellme.tellme.domains.survey.presentation;

import com.tellme.tellme.domains.survey.entity.*;
import com.tellme.tellme.domains.user.infrastructure.UserEntity;
import lombok.*;

import java.util.List;

public class SurveyDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class Answer {
        private List<AnswerContent> answerContentList;

        public SurveyCompletion toSurveyCompletion(Survey survey, UserEntity userEntity, String uniqueId) {
            return SurveyCompletion.builder()
                    .survey(survey)
                    .uniqueId(uniqueId)
                    .userEntity(userEntity)
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
    public static class SurveyInfo{
        private int userId;
        private int surveyId;
        private int userCount;
        private String nickname;
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

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class SurveyResultInfo{
        private String type;
        private String content;
        private int typeNumber;
        private List<SurveyResultKeywordInfo> keywordInfo;
        private String shortUrl;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class SurveyResultKeywordInfo{
        private String title;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class SurveyResultDetail{
        private String nickname;
        private List<SurveyCompletionWithAnswers> surveyCompletionWithAnswers;
        private String type;
        private List<SurveyResultKeywordInfo> feedBackKeywords;
        private List<SurveyResultKeywordInfo> selfKeywords;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class SurveyCompletionWithAnswers {
        private String question;
        private String answerToMe;
        private String answerToOther;

        public void setAnswerToOther(String answerToOther) {
            this.answerToOther = answerToOther;
        }
    }
}

