package com.tellme.tellme.domain.user.application;

//import com.tellme.tellme.common.config.JwtTokenProvider;
import com.tellme.tellme.domain.user.entity.User;
import com.tellme.tellme.domain.user.persistence.UserRepository;
import com.tellme.tellme.domain.user.presentation.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.tellme.tellme.domain.user.presentation.UserDto.*;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean checkUserByEmail(String email) {
        Optional<User> result = userRepository.findByEmail(email);
        if (result.isPresent()) return true;
        return false;
    }

    @Transactional(readOnly = true)
    public GetUserRes getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user null"));
        return new GetUserRes(user);
    }



}
