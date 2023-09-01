package com.tellme.tellme.domain.survey.application.port;

import com.tellme.tellme.domain.survey.entity.Survey;

import java.util.Optional;

public interface SurveyRepository {
    Optional<Survey> findById(int surveyId);
}
