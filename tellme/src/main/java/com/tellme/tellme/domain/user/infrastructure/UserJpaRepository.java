package com.tellme.tellme.domain.user.infrastructure;

import com.tellme.tellme.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailAndDeletedIsNull(String email);
    Optional<User> findByIdAndDeletedIsNull(int userId);

}
