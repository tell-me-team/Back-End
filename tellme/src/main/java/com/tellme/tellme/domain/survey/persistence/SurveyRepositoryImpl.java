package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.application.port.SurveyRepository;
import com.tellme.tellme.domain.survey.entity.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SurveyRepositoryImpl implements SurveyRepository {

    private final SurveyJpaRepository surveyJpaRepository;


    @Override
    public Optional<Survey> findById(int surveyId) {
        return surveyJpaRepository.findById(surveyId);
    }
}
