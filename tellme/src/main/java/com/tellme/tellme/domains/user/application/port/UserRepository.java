package com.tellme.tellme.domains.user.application.port;

import com.tellme.tellme.domains.survey.entity.SurveyCompletion;
import com.tellme.tellme.domains.user.infrastructure.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(int userId);

    List<SurveyCompletion> findByUniqueId(String uniqueId);

}
