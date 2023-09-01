package com.tellme.tellme.domain.survey.application.port;

import com.tellme.tellme.domain.survey.entity.SurveyResult;

public interface SurveyResultRepository {
    SurveyResult findByTypeNumber(int mostCommonDigit);
}
