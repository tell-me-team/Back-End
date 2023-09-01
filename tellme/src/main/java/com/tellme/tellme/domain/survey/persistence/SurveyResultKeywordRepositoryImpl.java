package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.application.port.SurveyResultKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SurveyResultKeywordRepositoryImpl implements SurveyResultKeywordRepository {
    private final SurveyResultKeywordJpaRepository surveyResultKeywordJpaRepository;
}
