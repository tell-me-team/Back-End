package com.tellme.tellme.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseResponse<T> {

    @JsonProperty
    private int code;
    @JsonProperty
    private HttpStatus status;
    @JsonProperty
    private String message;
    @JsonProperty
    private T data;

    public BaseResponse(HttpStatus status, String message, T data) {
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> of(HttpStatus httpStatus, String message, T data){
        return new BaseResponse<>(httpStatus, message, data);
    }

    public static <T> BaseResponse<T> of(HttpStatus httpStatus, T data) {
        return of(httpStatus, httpStatus.name(), data);
    }

    public static <T> BaseResponse<T> ok(T data) {
        return of(HttpStatus.OK, HttpStatus.OK.name(), data);
    }
}