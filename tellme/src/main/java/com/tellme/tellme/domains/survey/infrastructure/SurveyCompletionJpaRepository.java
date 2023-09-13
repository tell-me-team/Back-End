package com.tellme.tellme.domains.survey.infrastructure;

import com.tellme.tellme.domains.survey.entity.Survey;
import com.tellme.tellme.domains.survey.entity.SurveyCompletion;
import com.tellme.tellme.domains.user.infrastructure.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyCompletionJpaRepository extends JpaRepository<SurveyCompletion, Integer> {
    SurveyCompletion findByUniqueIdAndSurveyAndUserEntity(String uniqueId, Survey survey, UserEntity userEntity);

    List<SurveyCompletion> findByUserEntity(UserEntity userEntity);

    SurveyCompletion findByUserEntityAndUniqueIdAndSurvey(UserEntity createUserEntity, String userId, Survey survey);

    List<SurveyCompletion> findByUserEntityAndUniqueIdNotAndSurvey(UserEntity createUserEntity, String uniqueId, Survey survey);

    Optional<SurveyCompletion> findByUserEntityAndSurveyAndUniqueId(UserEntity userEntity, Survey survey, String uniqueId);

    List<SurveyCompletion> findByUniqueId(String uniqueId);
}
