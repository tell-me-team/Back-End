package com.tellme.tellme.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tellme.tellme.common.exception.ErrorStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseExceptionResponse {

    @JsonProperty
    private int code;
    @JsonProperty
    private HttpStatus status;
    @JsonProperty
    private String message;

    public BaseExceptionResponse() {
        this.code = status.value();
        this.status = status;
        this.message = message;
    }

    public BaseExceptionResponse(ErrorStatus errorStatus) {
        this.code = errorStatus.getStatus().value();
        this.status = errorStatus.getStatus();
        this.message = errorStatus.getMessage();
    }

    public BaseExceptionResponse(HttpStatus status, Exception exception) {
        this.code = status.value();
        this.status = status;
        this.message = exception.getMessage();
    }

    public BaseExceptionResponse(HttpStatus status, String message) {
        this.code = status.value();
        this.status = status;
        this.message = message;
    }


}
