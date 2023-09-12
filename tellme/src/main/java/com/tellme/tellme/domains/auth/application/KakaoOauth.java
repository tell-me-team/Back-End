package com.tellme.tellme.domains.auth.application;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.tellme.tellme.domains.auth.presentation.AuthDto.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoOauth implements SocialOauth{

    //applications.yml 에서 value annotation을 통해서 값을 받아온다.
    @Value("${spring.security.oauth2.client.provider.kakao.authorization_uri}")
    private String KAKAO_SNS_URL;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String KAKAO_SNS_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String KAKAO_SNS_CALLBACK_LOGIN_URL;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String KAKAO_SNS_CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.kakao.scope}")
    private String KAKAO_DATA_ACCESS_SCOPE;

    private final ObjectMapper objectMapper;

    // TODO. WebClient로 변경
    private final RestTemplate restTemplate;


    public ResponseEntity<String> requestAccessToken(String code) {
        String KAKAO_TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token";
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        // SET Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", "application/json");

        // SET Body
        params.add("grant_type", "authorization_code");
        params.add("client_id", KAKAO_SNS_CLIENT_ID);
        params.add("redirect_uri", KAKAO_SNS_CALLBACK_LOGIN_URL);
        params.add("code", code);
        params.add("client_secret", KAKAO_SNS_CLIENT_SECRET);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(KAKAO_TOKEN_REQUEST_URL, request, String.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return responseEntity;
        }
        return null;
    }

    public KakaoOAuthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        log.info("response.getBody() = {}", response.getBody());

        KakaoOAuthToken kakaoOAuthToken = objectMapper.readValue(response.getBody(), KakaoOAuthToken.class);
        return kakaoOAuthToken;

    }

    public ResponseEntity<String> requestUserInfo(KakaoOAuthToken oAuthToken) {
        String KAKAO_USERINFO_REQUEST_URL="https://kapi.kakao.com/v2/user/me";

        // Header에 accessToken을 담는다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+oAuthToken.getAccess_token());

        // HttpEntity를 하나 생성해 헤더를 담아서 restTemplate으로 통신
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(KAKAO_USERINFO_REQUEST_URL, HttpMethod.GET,request,String.class);

        log.info("response.getBody() = {}", response.getBody());

        return response;
    }

    public KakaoUser getUserInfo(ResponseEntity<String> userInfoRes) throws JsonProcessingException{
        KakaoUser kakaoUser = objectMapper.readValue(userInfoRes.getBody(), KakaoUser.class);
        return kakaoUser;
    }
}
