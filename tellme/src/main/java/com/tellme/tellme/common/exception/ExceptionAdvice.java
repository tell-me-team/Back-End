package com.tellme.tellme.common.exception;

import com.tellme.tellme.common.response.BaseExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public BaseExceptionResponse BaseExceptionHandle(BaseException exception) {
        log.warn("[BaseException] error message: {}", exception.getMessage());
        return new BaseExceptionResponse(exception.getErrorStatus());
    }

    @ExceptionHandler(Exception.class)
    public BaseExceptionResponse ExceptionHandle(Exception exception) {
        log.error("[unregistered exception has occured] error message: {}", exception.getMessage());
        return new BaseExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }
}
