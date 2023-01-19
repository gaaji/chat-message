package com.gaaji.chatmessage.global.exception;

public class ChatMessageException extends RuntimeException {

    private ErrorCode errorCode;

    public ChatMessageException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode errorCode() { return this.errorCode; }

}
