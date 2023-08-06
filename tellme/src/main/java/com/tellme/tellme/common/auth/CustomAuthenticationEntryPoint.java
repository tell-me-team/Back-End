package com.tellme.tellme.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tellme.tellme.common.response.BaseExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import static com.tellme.tellme.common.exception.ErrorStatus.AUTHORITY_ERROR;


// 인증 실패시 결과를 처리해주는 로직을 가지고 있는 클래스
@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;

    public CustomAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException exception) throws IOException {

        if (exception != null) {
            resolver.resolveException(request, response, null, (Exception) request.getAttribute("exception"));
        }else {
            ObjectMapper objectMapper = new ObjectMapper();
            log.info("[commence] 인증 예외 발생");

            response.setStatus(403);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(new BaseExceptionResponse(AUTHORITY_ERROR)));
        }

    }
}
