package com.gaaji.chatmessage.global.error.exception;

import com.gaaji.chatmessage.global.error.ErrorCode;

public class NotFoundSessionIdException extends ChatMessageException {
    public NotFoundSessionIdException(Class<?> errorClass) {
        super(errorClass, ErrorCode.NOT_FOUND_SESSION_ID_ERROR);
    }

}
