package com.gaaji.chatmessage.global.error;

import java.util.Objects;

public enum ErrorCode {
    SYSTEM_ERROR("C-100", "죄송합니다. 시스템에 오류가 발생하였습니다."),

    TOKEN_NULL_ERROR("C-101", "인증 토큰이 없습니다."),
    INVALIDATED_TOKEN_ERROR("C-102", "토큰 검증에 실패하였습니다."),
    MALFORMED_TOKEN_ERROR("C-103", "토큰의 형식이 유효하지 않습니다."),
    TOKEN_EXPIRATION_ERROR("C-104", "인증 토큰이 만료되었습니다."),
    INVALIDATED_TOKEN_PREFIX_ERROR("C-105", "토큰 타입이 유효하지 않습니다."),
    JSON_PROCESSING_ERROR("C-106", "JSON 처리과정에 오류가 발생하였습니다."),

    BAD_REQUEST_ERROR("C-107", "잘못된 요청입니다."),

    NOT_FOUND_SESSION_ID_ERROR("C-108", "세션 ID가 없습니다."),
    NOT_FOUND_USER_ID_ERROR("C-109", "유저 ID가 없습니다."),
    NOT_FOUND_ROOM_ID_ERROR("C-110", "채팅방 ID가 없습니다."),

    ;

    private String code;
    private String message;
    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() { return code; }
    public String getMessage() { return message; }

    public static ErrorCode getErrorCode(String code) {
        if (code == null) return ErrorCode.SYSTEM_ERROR;
        if (code.isBlank()) return ErrorCode.SYSTEM_ERROR;

        for(ErrorCode errorCode: ErrorCode.values()) {
            if (Objects.equals(errorCode.getCode(), code)) {
                return errorCode;
            }
        }
        return ErrorCode.SYSTEM_ERROR;
    }
}