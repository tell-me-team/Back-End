package com.tellme.tellme.domain.user.infrastructure;

import com.tellme.tellme.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmailAndDeletedIsNull(String email);
    Optional<UserEntity> findByIdAndDeletedIsNull(int userId);

}
