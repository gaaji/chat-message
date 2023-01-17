package com.gaaji.chatmessage.global.stomp;

import com.gaaji.chatmessage.global.constants.StringConstants;
import com.gaaji.chatmessage.global.exception.ErrorCodeConstants;
import com.gaaji.chatmessage.global.jwt.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        StompCommand command = accessor.getCommand();
        if(command == StompCommand.CONNECT) {
            log.info("[StompHandler] - WebSocket Connecting ...");

            log.info("[StompHandler] - Token Validating ...");

            String authorization = accessor.getFirstNativeHeader(StringConstants.TOKEN_HEADER_KEY);
            try {
                jwtProvider.validateToken(authorization);

            } catch (MalformedJwtException e ) {
                throw new MessageDeliveryException(ErrorCodeConstants.JWT_MALFORMED);

            } catch (ExpiredJwtException e) {
                throw new MessageDeliveryException(ErrorCodeConstants.JWT_EXPIRED);
            }

            log.info("[StompHandler] - WebSocket Connect");
        }
        return message;
    }
}
