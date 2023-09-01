package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.application.port.SurveyCompletionRepository;
import com.tellme.tellme.domain.survey.entity.Survey;
import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import com.tellme.tellme.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SurveyCompletionRepositoryImpl implements SurveyCompletionRepository {

    private final SurveyCompletionJpaRepository surveyCompletionJpaRepository;
    @Override
    public SurveyCompletion findByUniqueIdAndSurveyAndUser(String uniqueId, Survey survey, User user) {
        return surveyCompletionJpaRepository.findByUniqueIdAndSurveyAndUser(uniqueId, survey, user);
    }

    @Override
    public List<SurveyCompletion> findByUser(User user) {
        return surveyCompletionJpaRepository.findByUser(user);
    }

    @Override
    public SurveyCompletion findByUserAndUniqueIdAndSurvey(User createUser, String userId, Survey survey) {
        return surveyCompletionJpaRepository.findByUserAndUniqueIdAndSurvey(createUser, userId, survey);
    }

    @Override
    public List<SurveyCompletion> findByUserAndUniqueIdNotAndSurvey(User createUser, String uniqueId, Survey survey) {
        return surveyCompletionJpaRepository.findByUserAndUniqueIdNotAndSurvey(createUser, uniqueId, survey);
    }

    @Override
    public Optional<SurveyCompletion> findByUserAndSurveyAndUniqueId(User user, Survey survey, String uniqueId) {
        return surveyCompletionJpaRepository.findByUserAndSurveyAndUniqueId(user, survey, uniqueId);
    }

    @Override
    public List<SurveyCompletion> findByUniqueId(String uniqueId) {
        return surveyCompletionJpaRepository.findByUniqueId(uniqueId);
    }

    @Override
    public SurveyCompletion save(SurveyCompletion surveyCompletion) {
        return surveyCompletionJpaRepository.save(surveyCompletion);
    }
}
