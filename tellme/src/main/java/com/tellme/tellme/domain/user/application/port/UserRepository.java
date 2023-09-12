package com.tellme.tellme.domain.user.application.port;

import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import com.tellme.tellme.domain.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(int userId);

    List<SurveyCompletion> findByUniqueId(String uniqueId);

}
