package com.tellme.tellme.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{

    private ErrorStatus errorStatus;

    public BaseException(String message, ErrorStatus errorStatus){
        super(message);
        this.errorStatus = errorStatus;
    }

    public BaseException(ErrorStatus errorStatus){
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }
}
