package com.gaaji.chatmessage.global.error.exception;

import com.gaaji.chatmessage.global.error.ErrorCode;

public class NotFoundUserIdException extends ChatMessageException {
    public NotFoundUserIdException(Class<?> errorClass) {
        super(errorClass, ErrorCode.NOT_FOUND_USER_ID_ERROR);
    }

}
