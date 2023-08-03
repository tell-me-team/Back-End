package com.tellme.tellme.domain.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1/users")
@RestController
public class UserControllerV1 {


    @GetMapping("/test")
    @Operation(summary = "authentication 테스트")
    public String test(Authentication authentication) {
        log.info("authentication : {}", authentication);
        log.info("principal : {}", authentication.getPrincipal());
        return "oauth-test success!!";
    }

}

