package com.tellme.tellme.common.exception;

import com.tellme.tellme.common.response.BaseExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.tellme.tellme.common.exception.ErrorStatus.*;


@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(SignatureException.class)
    public BaseExceptionResponse handleSignatureException(SignatureException exception) {
        log.warn("[SignatureException] error message: {}", exception.getMessage());
        return new BaseExceptionResponse(SIGNATURE_WRONG_VALID);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MalformedJwtException.class)
    public BaseExceptionResponse handleMalformedJwtException(MalformedJwtException exception) {
        log.warn("[SignatureException] error message: {}", exception.getMessage());
        return new BaseExceptionResponse(MALFORMED_TOKEN);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public BaseExceptionResponse handleExpiredJwtException(ExpiredJwtException exception) {
        log.warn("[SignatureException] error message: {}", exception.getMessage());
        return new BaseExceptionResponse(EXPIRED_ACCESS_TOKEN);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public BaseExceptionResponse handleIllegalArgumentExceptionException(IllegalArgumentException exception) {
        log.warn("[IllegalArgumentException] error message: {}", exception.getMessage());
        return new BaseExceptionResponse(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(BaseException.class)
    public BaseExceptionResponse BaseExceptionHandle(BaseException exception) {
        log.warn("[BaseException] error message: {}", exception.getMessage());
        return new BaseExceptionResponse(exception.getErrorStatus());
    }

    @ExceptionHandler(Exception.class)
    public BaseExceptionResponse ExceptionHandle(Exception exception) {
        log.error("[unregistered exception has occured] error class: {}", exception.getClass());
        log.error("[unregistered exception has occured] error message: {}", exception.getMessage());
        return new BaseExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }
}
