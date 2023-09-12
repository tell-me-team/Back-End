package com.tellme.tellme.domain.user.application.port;

import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import com.tellme.tellme.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findById(int userId);

    List<SurveyCompletion> findByUniqueId(String uniqueId);

}
