package com.tellme.tellme.domains.auth.presentation;

import com.tellme.tellme.common.enums.SocialLoginType;
import com.tellme.tellme.common.response.BaseResponse;
import com.tellme.tellme.domains.auth.application.OAuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.tellme.tellme.domains.auth.presentation.AuthDto.*;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1/auth")
@RestController
public class AuthControllerV1 {

    private final OAuthService oAuthService;

    @ResponseBody
    @GetMapping(value = "{socialLoginType}/login") // FIXME. POST 요청으로 수정
    @Operation(summary = "소셜 로그인")
    public BaseResponse<GetSocialOAuthRes> socialLoginCallback(
            @PathVariable(name = "socialLoginType") String socialLoginPath,
            @RequestParam(name = "code") String code) throws IOException {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code : {}", code);
        SocialLoginType socialLoginType = SocialLoginType.valueOf(socialLoginPath.toUpperCase());
        GetSocialOAuthRes getSocialOAuthRes = oAuthService.oAuthLoginOrJoin(socialLoginType, code);
        return BaseResponse.ok(getSocialOAuthRes);
    }

}
