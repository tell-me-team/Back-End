package com.tellme.tellme.domain.auth.presentation;

import com.tellme.tellme.common.enums.SocialLoginType;
import com.tellme.tellme.common.response.BaseResponse;
import com.tellme.tellme.domain.auth.application.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.tellme.tellme.domain.auth.presentation.AuthDto.*;


@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final OAuthService oAuthService;

    @ResponseBody
    @GetMapping(value = "/auth/{socialLoginType}/login")
    public BaseResponse<GetSocialOAuthRes> socialLoginCallback(
            @PathVariable(name = "socialLoginType") String socialLoginPath,
            @RequestParam(name = "code") String code) throws IOException {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code : {}", code);
        SocialLoginType socialLoginType = SocialLoginType.valueOf(socialLoginPath.toUpperCase());
        GetSocialOAuthRes getSocialOAuthRes = oAuthService.oAuthLoginOrJoin(socialLoginType, code);
        return BaseResponse.ok(getSocialOAuthRes);
    }

}
