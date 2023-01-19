package com.gaaji.chatmessage.global.stomp;

import com.gaaji.chatmessage.global.exception.ChatMessageException;
import com.gaaji.chatmessage.global.exception.ErrorCode;
import com.gaaji.chatmessage.global.exception.ExceptionHandlerConstants;
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

    public StompErrorHandler() {
        super();
    }

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {

        if ( ex instanceof ChatMessageException ) {
            return handleCustomException(((ChatMessageException) ex).errorCode());
        }

        if (ex instanceof MessageDeliveryException) {

            switch (Objects.requireNonNull(ex.getMessage())) {
                case ExceptionHandlerConstants.JWT_NULL:
                    return handleTokenException(ErrorCode.TOKEN_NULL_ERROR);

                case ExceptionHandlerConstants.JWT_INVALIDATED:
                    return handleTokenException(ErrorCode.INVALIDATED_TOKEN_ERROR);

                case ExceptionHandlerConstants.JWT_EXPIRED:
                    return handleTokenException(ErrorCode.TOKEN_EXPIRATION_ERROR);

                case ExceptionHandlerConstants.JWT_MALFORMED:
                    return handleTokenException(ErrorCode.MALFORMED_TOKEN_ERROR);

                case ExceptionHandlerConstants.JWT_INVALIDATED_PREFIX:
                    return handleTokenException(ErrorCode.INVALIDATED_TOKEN_PREFIX_ERROR);
            }
        }
        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    /** JWT 토큰 만료 예외 처리 */
    private Message<byte[]> handleTokenException(ErrorCode errorCode) {
        return prepareErrorMessage(errorCode);
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
