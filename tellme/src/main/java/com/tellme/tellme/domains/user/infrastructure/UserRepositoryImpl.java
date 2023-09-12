package com.tellme.tellme.domains.user.infrastructure;

import com.tellme.tellme.domains.survey.entity.SurveyCompletion;
import com.tellme.tellme.domains.survey.persistence.SurveyCompletionRepository;
import com.tellme.tellme.domains.user.application.port.UserRepository;
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
    public Optional<UserEntity> findByEmail(String email) {
        return userJpaRepository.findByEmailAndDeletedIsNull(email);
    }

    @Override
    public Optional<UserEntity> findById(int userId) {
        return userJpaRepository.findByIdAndDeletedIsNull(userId);
    }

    @Override
    public List<SurveyCompletion> findByUniqueId(String uniqueId) {
        return surveyCompletionRepository.findByUniqueId(uniqueId);
    }
}
