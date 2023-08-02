package com.tellme.tellme.domain.auth.application;

import org.springframework.http.ResponseEntity;

public interface SocialOauth {

    ResponseEntity<String> requestAccessToken(String code);
}
