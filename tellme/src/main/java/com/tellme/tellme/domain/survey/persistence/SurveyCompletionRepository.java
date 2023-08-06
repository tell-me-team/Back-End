package com.tellme.tellme.domain.survey.persistence;

import com.tellme.tellme.domain.survey.entity.Survey;
import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import com.tellme.tellme.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyCompletionRepository extends JpaRepository<SurveyCompletion, Integer> {
    SurveyCompletion findByUniqueId(String uniqueId);

    List<SurveyCompletion> findByUser(User user);

    SurveyCompletion findByUserAndUniqueIdAndSurvey(User createUser, String userId, Survey survey);

    List<SurveyCompletion> findBySurveyIdAndUserAndUniqueIdNot(int surveyId, User user, String uniqueId);

    List<SurveyCompletion> findByUserAndUniqueIdNotAndSurvey(User createUser, String uniqueId, Survey survey);

    Optional<SurveyCompletion> findByUserAndSurveyAndUniqueId(User user, Survey survey, String uniqueId);
}
