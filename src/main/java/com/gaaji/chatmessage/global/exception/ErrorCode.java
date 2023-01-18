package com.gaaji.chatmessage.global.exception;

public enum ErrorCode {
    TOKEN_NULL_ERROR("C-101", "인증 토큰이 없습니다."),
    INVALIDATED_TOKEN_ERROR("C-102", "토큰 검증에 실패하였습니다."),
    MALFORMED_TOKEN_ERROR("C-103", "토큰의 형식이 유효하지 않습니다."),
    TOKEN_EXPIRATION_ERROR("C-104", "인증 토큰이 만료되었습니다."),
    INVALIDATED_TOKEN_PREFIX_ERROR("C-105", "토큰 타입이 유효하지 않습니다."),
    ;
    private String code;
    private String message;
    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() { return code; }
    public String getMessage() { return message; }
}