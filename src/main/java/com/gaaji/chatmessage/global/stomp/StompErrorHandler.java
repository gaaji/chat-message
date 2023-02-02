package com.gaaji.chatmessage.global.stomp;

import com.gaaji.chatmessage.global.error.exception.ChatMessageException;
import com.gaaji.chatmessage.global.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Reference : <a href="https://velog.io/@jkijki12/%EC%B1%84%ED%8C%85-STOMP-JWT">STOMP-JWT Reference Blog</a>
 */
@Slf4j
@Component
public class StompErrorHandler extends StompSubProtocolErrorHandler {

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        Throwable exception = ex;

        if (exception instanceof MessageDeliveryException) {

            if (exception.getCause() instanceof ChatMessageException) {
                ChatMessageException e = (ChatMessageException) exception.getCause();
                return handleCustomException(e.errorCode());
            }

            Objects.requireNonNull(exception.getMessage());
            ErrorCode errorCode = ErrorCode.getErrorCode(exception.getMessage());
            return handleCustomException(errorCode);
        }
        return handleUnknownException();
    }

    private Message<byte[]> handleUnknownException() {
        return prepareErrorMessage(ErrorCode.SYSTEM_ERROR);
    }

    private Message<byte[]> handleCustomException(ErrorCode errorCode) {
        return prepareErrorMessage(errorCode);
    }

    private Message<byte[]> prepareErrorMessage(ErrorCode errorCode) {
        log.error("[StompErrorHandler] - Error code= {}, message= {}", errorCode.getCode(), errorCode.getMessage());
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);

        String code = errorCode.getMessage();

        accessor.setMessage(errorCode.getCode());
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(code.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }

}
