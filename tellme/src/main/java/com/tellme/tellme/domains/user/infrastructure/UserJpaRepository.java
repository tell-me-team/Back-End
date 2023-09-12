package com.tellme.tellme.domains.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmailAndDeletedIsNull(String email);
    Optional<UserEntity> findByIdAndDeletedIsNull(int userId);

}
