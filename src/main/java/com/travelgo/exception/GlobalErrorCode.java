package com.travelgo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode {
    NOT_SUPPORTED_URI_ERROR(HttpStatus.NOT_FOUND, "GLOBAL_001", "지원하지 않는 URL입니다."),
    NOT_SUPPORTED_METHOD_ERROR(HttpStatus.METHOD_NOT_ALLOWED, "GLOBAL_002", "지원하지 않는 HTTP Method 요청입니다."),

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON-001", "유효성 검증에 실패했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-002", "서버에서 처리할 수 없습니다."),

    UNAUTHORIZED(HttpStatus.BAD_REQUEST, "ACCOUNT-001", "인증에 실패하였습니다."),
    ACCOUNT_NOT_FOUND(HttpStatus.BAD_REQUEST, "ACCOUNT-001", "계정을 찾을 수 없습니다."),

    ACCOUNT_DUPLICATION(HttpStatus.BAD_REQUEST, "USER-001", "이미 사용중인 계정입니다."),
    NICKNAME_DUPLICATION(HttpStatus.BAD_REQUEST, "USER-002", "이미 사용중인 닉네임입니다."),
    NICKNAME_DISALLOWED(HttpStatus.BAD_REQUEST, "USER-003", "부적절한 닉네임입니다."),
    ACCOUNT_NO_EXIST(HttpStatus.BAD_REQUEST, "USER-004", "신규 유저입니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
