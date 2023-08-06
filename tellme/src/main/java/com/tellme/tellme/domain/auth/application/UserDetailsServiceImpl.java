package com.tellme.tellme.domain.auth.application;

import com.tellme.tellme.domain.user.entity.User;
import com.tellme.tellme.domain.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tellme.tellme.common.enums.UserRole.ANONYMOUS;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) {
        LOGGER.info("[loadUserByUsername] loadUserByUsername 수행. userId : {}", userId);

        return userRepository.findById(Integer.valueOf(userId)).get();
    }

}