package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.application.port.SurveyAnswerRepository;
import com.tellme.tellme.domain.survey.entity.SurveyAnswer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SurveyAnswerRepositoryImpl implements SurveyAnswerRepository {

    private final SurveyAnswerJpaRepository surveyAnswerJpaRepository;


    @Override
    public SurveyAnswer save(SurveyAnswer surveyAnswer) {
        return surveyAnswerJpaRepository.save(surveyAnswer);
    }
}
