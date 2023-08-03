package com.tellme.tellme.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tellme.tellme.common.exception.BaseException;
import com.tellme.tellme.common.response.BaseExceptionResponse;
import com.tellme.tellme.common.response.BaseResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

import static com.tellme.tellme.common.exception.ErrorStatus.EXPIRED_ACCESS_TOKEN;


public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtTokenProvider.resolveToken(servletRequest);
            LOGGER.info("[doFilterInternal] token 값 추출 완료. token : {}", token);

            LOGGER.info("[doFilterInternal] token 값 유효성 체크 시작");
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                LOGGER.info("[doFilterInternal] token 값 유효성 체크 완료");
            }
        }catch (Exception exception) {
            servletRequest.setAttribute("exception", exception);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
