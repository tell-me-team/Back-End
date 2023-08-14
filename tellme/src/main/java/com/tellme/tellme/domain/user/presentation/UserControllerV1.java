package com.tellme.tellme.domain.user.presentation;

import com.tellme.tellme.common.response.BaseResponse;
import com.tellme.tellme.domain.user.application.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.tellme.tellme.domain.user.presentation.UserDto.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1/users")
@RestController
public class UserControllerV1 {

    private final UserService userService;

    @GetMapping("/test")
    @Operation(summary = "authentication 테스트", description = "토큰 필수 | authentication 객체 조회 테스트")
    public String test(Authentication authentication) {
        log.info("authentication : {}", authentication);
        log.info("principal : {}", authentication.getPrincipal());
        return "oauth-test success!!";
    }

    @CrossOrigin("*")
    @GetMapping("/info")
    @Operation(summary = "user 정보", description = "토큰 필수 | 유저 정보 및 수행한 내 설문 조회")
    public BaseResponse<UserInfo> info(Authentication authentication){
        return BaseResponse.ok(userService.info(authentication));
    }


}

