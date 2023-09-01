package com.tellme.tellme.domain.survey.application.port;

import com.tellme.tellme.domain.survey.entity.Survey;
import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import com.tellme.tellme.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface SurveyCompletionRepository {
    SurveyCompletion findByUniqueIdAndSurveyAndUser(String uniqueId, Survey survey, User user);

    List<SurveyCompletion> findByUser(User user);

    SurveyCompletion findByUserAndUniqueIdAndSurvey(User createUser, String userId, Survey survey);

    List<SurveyCompletion> findByUserAndUniqueIdNotAndSurvey(User createUser, String uniqueId, Survey survey);

    Optional<SurveyCompletion> findByUserAndSurveyAndUniqueId(User user, Survey survey, String uniqueId);

    List<SurveyCompletion> findByUniqueId(String uniqueId);

    SurveyCompletion save(SurveyCompletion toSurveyCompletion);
}
