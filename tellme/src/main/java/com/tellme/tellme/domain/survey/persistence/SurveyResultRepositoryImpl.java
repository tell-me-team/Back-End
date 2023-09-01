package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.application.port.SurveyResultRepository;
import com.tellme.tellme.domain.survey.entity.SurveyResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SurveyResultRepositoryImpl implements SurveyResultRepository {

    private final SurveyResultJpaRepository surveyResultJpaRepository;

    @Override
    public SurveyResult findByTypeNumber(int mostCommonDigit) {
        return surveyResultJpaRepository.findByTypeNumber(mostCommonDigit);
    }
}
