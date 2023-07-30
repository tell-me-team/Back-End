package com.tellme.tellme.domain.survey.application;

import com.tellme.tellme.domain.survey.entity.Survey;
import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import com.tellme.tellme.domain.survey.persistence.SurveyAnswerRepository;
import com.tellme.tellme.domain.survey.persistence.SurveyCompletionRepository;
import com.tellme.tellme.domain.survey.persistence.SurveyRepository;
import com.tellme.tellme.domain.survey.presentation.SurveyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyCompletionRepository surveyCompletionRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;
    private final SurveyRepository surveyRepository;


    public void saveAnswer(int surveyId, String userId, SurveyDto.Answer answer) {
        answer.setSurvey(surveyRepository.findById(surveyId).get());

        SurveyCompletion surveyCompletion = surveyCompletionRepository.save(answer.toSurveyCompletion());
        for (SurveyDto.AnswerContent answerContent : answer.getAnswerContentList()) {
            surveyAnswerRepository.save(answerContent.toSurveyAnswer(surveyCompletion));
        }
    }
}
