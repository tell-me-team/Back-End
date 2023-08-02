package com.tellme.tellme.domain.user.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {


    @GetMapping("/test")
    public String test(Authentication authentication) {
        log.info("authentication : {}", authentication);
        log.info("principal : {}", authentication.getPrincipal());
        return "oauth-test success!!";
    }

}

