package com.tellme.tellme.domain.auth.application;

import com.tellme.tellme.domain.user.infrastructure.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) {
        LOGGER.info("[loadUserByUsername] loadUserByUsername 수행. userId : {}", userId);

        return userJpaRepository.findById(Integer.valueOf(userId)).get();
    }

}