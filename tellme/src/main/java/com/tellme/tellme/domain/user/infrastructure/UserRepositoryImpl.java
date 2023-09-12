package com.tellme.tellme.domain.user.infrastructure;

import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import com.tellme.tellme.domain.survey.persistence.SurveyCompletionRepository;
import com.tellme.tellme.domain.user.application.port.UserRepository;
import com.tellme.tellme.domain.user.entity.User;
import com.tellme.tellme.domain.user.presentation.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final SurveyCompletionRepository surveyCompletionRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmailAndDeletedIsNull(email);
    }

    @Override
    public Optional<User> findById(int userId) {
        return userJpaRepository.findByIdAndDeletedIsNull(userId);
    }

    @Override
    public List<SurveyCompletion> findByUniqueId(String uniqueId) {
        return surveyCompletionRepository.findByUniqueId(uniqueId);
    }
}
