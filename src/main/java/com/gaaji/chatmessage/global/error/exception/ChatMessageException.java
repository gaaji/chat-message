package com.gaaji.chatmessage.global.error.exception;

import com.gaaji.chatmessage.global.error.ErrorCode;

public class ChatMessageException extends RuntimeException {

    private Class<?> errorClass;
    private ErrorCode errorCode;

    public ChatMessageException(Class<?> errorClass, ErrorCode errorCode) {
        this.errorClass = errorClass;
        this.errorCode = errorCode;
    }

    public ChatMessageException(ErrorCode errorCode) {
        this.errorClass = null;
        this.errorCode = errorCode;
    }

    public String errorClass() {
        return this.errorClass != null ? this.errorClass.getSimpleName() : "Unknown Class";
    }

    public ErrorCode errorCode() { return this.errorCode; }

    @Override
    public String toString() {
        String string = "{" +
                "\"code\": " + "\"" + errorCode.getCode() + "\"" +
                "\"message\": " + "\"" +errorCode.getMessage() + "\"" +
                "}";
        return string;
    }

}
