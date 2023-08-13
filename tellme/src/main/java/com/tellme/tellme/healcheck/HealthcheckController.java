package com.tellme.tellme.healcheck;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {

    @GetMapping("/healthcheck")
    @Operation(summary = "서버 상태 확인 체크", description = "토큰 필수X | 서버 상태 체크 테스트")
    public String healthcheck(){
        return "ok";
    }

}

