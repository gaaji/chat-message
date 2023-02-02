package com.gaaji.chatmessage.global.error.exception;

import com.gaaji.chatmessage.global.error.ErrorCode;

public class NotFoundRoomIdException extends ChatMessageException {
    public NotFoundRoomIdException(Class<?> errorClass) {
        super(errorClass, ErrorCode.NOT_FOUND_ROOM_ID_ERROR);
    }
}
