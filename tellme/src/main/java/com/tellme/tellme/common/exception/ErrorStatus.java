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
    SURVEY_ALREADY_COMPLETED(HttpStatus.CONFLICT, "이미 설문조사에 참여했습니다."),
    SURVEY_ANSWER_INSUFFICIENT(HttpStatus.BAD_REQUEST, "설문 답변에 개수가 안 맞습니다."),
    SURVEY_NOT_CREATE(HttpStatus.NOT_FOUND, "설문을 생성하지 않았습니다."),


    // AUTH
    CERTIFICATION_ERROR(HttpStatus.UNAUTHORIZED, "인증에 실패하였습니다."),
    AUTHORITY_ERROR(HttpStatus.FORBIDDEN, "권한이 허용되지 않은 유저입니다."),
    SIGNATURE_WRONG_VALID(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰입니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    ;

    private final HttpStatus status;
    private final String message;

}
