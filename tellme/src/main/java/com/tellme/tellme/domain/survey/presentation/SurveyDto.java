package com.tellme.tellme.domain.survey.presentation;

import com.tellme.tellme.domain.survey.entity.Question;
import com.tellme.tellme.domain.survey.entity.Survey;
import com.tellme.tellme.domain.survey.entity.SurveyAnswer;
import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import com.tellme.tellme.domain.user.entity.User;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class SurveyDto {

    @Data
    public static class Answer {
        private Survey survey;
        private User user;
        private String uuid;
        private List<AnswerContent> answerContentList;

        public SurveyCompletion toSurveyCompletion() {
            return SurveyCompletion.builder()
                    .survey(survey)
                    .user(user)
                    .uuid(uuid)
                    .build();
        }
    }

    @Data
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

    @Data
    @ToString
    public static class SurveyCompletionWithAnswers {
        private String question;
        private Character answerToMe;
        private String answerToOther;
    }
}

