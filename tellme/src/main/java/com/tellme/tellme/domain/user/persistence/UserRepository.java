package com.tellme.tellme.domain.user.persistence;

import com.tellme.tellme.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailAndDeletedIsNull(String email);
    Optional<User> findByIdAndDeletedIsNull(int userId);

}
