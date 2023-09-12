package com.tellme.tellme.domains.auth.application;

import org.springframework.http.ResponseEntity;

public interface SocialOauth {

    ResponseEntity<String> requestAccessToken(String code);
}
