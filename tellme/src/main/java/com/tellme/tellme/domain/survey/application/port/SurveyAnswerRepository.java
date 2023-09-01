package com.tellme.tellme.domain.survey.application.port;

import com.tellme.tellme.domain.survey.entity.SurveyAnswer;

public interface SurveyAnswerRepository {
    SurveyAnswer save(SurveyAnswer toSurveyAnswer);
}
