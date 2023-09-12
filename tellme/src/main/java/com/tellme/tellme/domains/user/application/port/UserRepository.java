package com.tellme.tellme.domains.user.application.port;

import com.tellme.tellme.domains.survey.entity.SurveyCompletion;
import com.tellme.tellme.domains.user.domain.User;
import com.tellme.tellme.domains.user.infrastructure.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findById(int userId);

    List<SurveyCompletion> findByUniqueId(String uniqueId);

}
