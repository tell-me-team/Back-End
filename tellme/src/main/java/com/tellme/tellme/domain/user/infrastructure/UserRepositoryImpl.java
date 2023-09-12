package com.tellme.tellme.domain.user.infrastructure;

import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import com.tellme.tellme.domain.survey.persistence.SurveyCompletionRepository;
import com.tellme.tellme.domain.user.application.port.UserRepository;
import com.tellme.tellme.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserEntityRepository userEntityRepository;
    private final SurveyCompletionRepository surveyCompletionRepository;

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userEntityRepository.findByEmailAndDeletedIsNull(email);
    }

    @Override
    public Optional<UserEntity> findById(int userId) {
        return userEntityRepository.findByIdAndDeletedIsNull(userId);
    }

    @Override
    public List<SurveyCompletion> findByUniqueId(String uniqueId) {
        return surveyCompletionRepository.findByUniqueId(uniqueId);
    }
}
