package com.gaaji.chatmessage.global.stomp;

import com.gaaji.chatmessage.domain.service.KafkaService;
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

    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;
    private final KafkaService kafkaService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        StompCommand command = accessor.getCommand();
        if(command == StompCommand.CONNECT) {
            connecting(accessor);
        } else if(command == StompCommand.DISCONNECT) {
            log.info("Disconnect");
            disconnecting();
        }
        return message;
    }

    private void connecting(StompHeaderAccessor accessor) {
        log.info("[StompHandler] - WebSocket Connecting ...");
        log.info("[StompHandler] - Token Validating ...");

        String authorization = accessor.getFirstNativeHeader("WebSocketToken");
        try {
            jwtProvider.validateToken(authorization);

        } catch (MalformedJwtException e ) {
            throw new MessageDeliveryException(ErrorCodeConstants.JWT_MALFORMED);

        } catch (ExpiredJwtException e) {
            throw new MessageDeliveryException(ErrorCodeConstants.JWT_EXPIRED);
        }
        log.info("[StompHandler] - WebSocket Connect");

        String userId = "qwer";
        kafkaService.notifyOnline(userId);
    }

    private void disconnecting() {
        String userId = "qwer";
        kafkaService.notifyOffline(userId);
    }

}
