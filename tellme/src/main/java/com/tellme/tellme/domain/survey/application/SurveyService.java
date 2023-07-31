package com.tellme.tellme.domain.survey.application;

import com.tellme.tellme.domain.survey.entity.Question;
import com.tellme.tellme.domain.survey.entity.SurveyAnswer;
import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import com.tellme.tellme.domain.survey.persistence.*;
import com.tellme.tellme.domain.survey.presentation.SurveyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyCompletionRepository surveyCompletionRepository;
    private final SurveyCompletionQueryRepository surveyCompletionQueryRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;



    public void saveAnswer(int surveyId, String userId, SurveyDto.Answer answer) {
        answer.setSurvey(surveyRepository.findById(surveyId).get());

        SurveyCompletion surveyCompletion = surveyCompletionRepository.save(answer.toSurveyCompletion());
        for (SurveyDto.AnswerContent answerContent : answer.getAnswerContentList()) {
            Question question = questionRepository.findById(answerContent.getQuestion()).get();
            surveyAnswerRepository.save(answerContent.toSurveyAnswer(surveyCompletion,question));
        }
    }

    public void getSurveyResult(int userId, int surveyId) {
        SurveyCompletion surveyCompletion = surveyCompletionQueryRepository.findByUserIdAndSurveyId(userId, surveyId);
        List<SurveyAnswer> surveyAnswerList = surveyAnswerRepository.findBySurveyCompletion(surveyCompletion);

    }
}
