package com.tellme.tellme.domains.user.infrastructure;

import com.tellme.tellme.domains.survey.entity.SurveyCompletion;
import com.tellme.tellme.domains.survey.infrastructure.SurveyCompletionJpaRepository;
import com.tellme.tellme.domains.user.application.port.UserRepository;
import com.tellme.tellme.domains.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final SurveyCompletionJpaRepository surveyCompletionJpaRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmailAndDeletedIsNull(email).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findById(int userId) {
        return userJpaRepository.findByIdAndDeletedIsNull(userId).map(UserEntity::toModel);
    }

    @Override
    public List<SurveyCompletion> findByUniqueId(String uniqueId) {
        return surveyCompletionJpaRepository.findByUniqueId(uniqueId);
    }
}
