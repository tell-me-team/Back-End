package com.tellme.tellme.common.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tellme.tellme.common.response.BaseExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.tellme.tellme.common.exception.ErrorStatus.AUTHORITY_ERROR;


// 권한이 없는 예외가 발생했을 경우 핸들링하는 클래스
@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException exception) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        log.info("[handle] 권한 예외 발생");

        response.setStatus(403);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(new BaseExceptionResponse(AUTHORITY_ERROR)));

    }
}
