package com.tellme.tellme.domain.survey.presentation;

import com.tellme.tellme.domain.survey.entity.*;
import com.tellme.tellme.domain.user.entity.User;
import lombok.*;

import java.util.List;

public class SurveyDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class Answer {
        private String uniqueId;
        private List<AnswerContent> answerContentList;

        public SurveyCompletion toSurveyCompletion(Survey survey, User user) {
            return SurveyCompletion.builder()
                    .survey(survey)
                    .user(user)
                    .uniqueId(uniqueId)
                    .build();
        }

        public void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @ToString
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
        private int userId;
        private int surveyId;
        private int userCount;

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
}

