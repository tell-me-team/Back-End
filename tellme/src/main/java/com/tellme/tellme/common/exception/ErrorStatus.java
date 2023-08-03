package com.tellme.tellme.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus {

    // COMMON
    RESOURCE_NOT_VALID(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),
    CLIENT_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "잘못된 요청입니다. 요청을 확인해주세요."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에  문제가 생겼습니다. 잠시 후 다시 시도해주세요."),

    // SURVEY
    SURVEY_ALREADY_COMPLETED(HttpStatus.CONFLICT, "이미 설문조사에 참여했습니다.");

    private final HttpStatus status;
    private final String message;

}
