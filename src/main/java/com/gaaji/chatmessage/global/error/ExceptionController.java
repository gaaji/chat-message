package com.gaaji.chatmessage.global.error;

import com.gaaji.chatmessage.global.constants.StompConstants;
import com.gaaji.chatmessage.global.error.exception.ChatMessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @MessageExceptionHandler(value = {ChatMessageException.class, Exception.class})
    @SendToUser(StompConstants.ENDPOINT_QUEUE_ERROR)
    public String handleException(Throwable e) {
        ChatMessageException exception;

        if(!(e instanceof ChatMessageException)) {
            exception = new ChatMessageException(ErrorCode.SYSTEM_ERROR);
        } else {
            exception = (ChatMessageException) e;
        }

        log.error("[Exception Occurred] class = {}, code = {}, message = {}", exception.errorClass(), exception.errorCode().getCode(), exception.errorCode().getMessage());

        return exception.toString();
    }
}
