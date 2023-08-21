package com.tellme.tellme.common.auth;


import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    @Value("${constant.jwt.secret-key}")
    private String secretKey;

    @Value("${constant.jwt.access-token-expiration-time}")
    private final long ACCESS_TOKEN_EXPIRATION_TIME = 43200000L; // 12시간

    @Value("${constant.jwt.refresh-token-expiration-time}")
    private final long REFRESH_TOKEN_EXPIRATION_TIME = 604800000L; // 7일



    // SecretKey 에 대해 인코딩 수행
    @PostConstruct
    protected void init() {
        log.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        log.info("[init] 인코딩 전 KEY: {}", secretKey);
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        log.info("[init] 인코딩 후 KEY: {}", secretKey);
        log.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }


    // JWT accessToken 생성
    public String createAccessToken(int userUid, List<String> roles) {

        log.info("[AccessToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(Integer.toString(userUid));
        claims.put("roles", roles);

        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
                .compact();

        log.info("[AccessToken] 토큰 생성 완료");
        return token;
    }

    // JWT refreshToken 생성
    public String createRefreshToken(int userUid, List<String> roles) {

        log.info("[RefreshToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(Integer.toString(userUid));
        claims.put("roles", roles);

        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
                .compact();

        log.info("[RefreshToken] 토큰 생성 완료");
        return token;
    }


    // JWT 토큰으로 인증 정보 조회
    public Authentication getAuthentication(String token) {
        log.info("[getAuthentication] 토큰 인증 정보 조회 시작");

        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));

        log.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}",
                userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }


    // JWT 토큰에서 회원 구별 정보 추출
    public String getUsername(String token) {

        log.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
        String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
                .getSubject();
        log.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}", info);
        return info;
    }



    // HTTP Request Header 에 설정된 토큰 값을 가져옴
    public String resolveToken(HttpServletRequest request) {
        log.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
        return request.getHeader("X-AUTH-TOKEN");
    }


    // JWT 토큰의 유효성 + 만료일 체크
    public boolean validateToken(String token) {

        log.info("[validateToken] 토큰 유효 체크 시작");

        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        log.info("[validateToken] 토큰 유효 체크 완료");
        return !claims.getBody().getExpiration().before(new Date());

    }

}
