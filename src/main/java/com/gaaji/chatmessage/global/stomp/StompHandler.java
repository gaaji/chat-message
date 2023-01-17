package com.gaaji.chatmessage.global.stomp;

import com.gaaji.chatmessage.domain.service.KafkaService;
import com.gaaji.chatmessage.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;
    private final KafkaService kafkaService;
    private static Map<String, String> sessions = new HashMap<>();

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        StompCommand command = accessor.getCommand();

        if(command == StompCommand.CONNECT) {
            connecting(accessor);
        }

        return message;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("[StompHandler] - WebSocket Disconnecting ...");
        String sessionId = event.getSessionId();

        if( sessions.containsKey(sessionId) ) {
            String userId = sessions.remove(sessionId);

            disconnecting(userId);

            log.info("[StompHandler] - WebSocket Disconnect");
        }
    }

    private void connecting(StompHeaderAccessor accessor) {
        log.info("[StompHandler] - WebSocket Connecting ...");

        String authorization = accessor.getFirstNativeHeader("WebSocketToken");
        jwtProvider.validateToken(authorization);

        String sessionId = accessor.getSessionId();
        String userId = "qwer"; // TODO Get User Id in Header

        sessions.put(sessionId, userId);

        kafkaService.notifyOnline(userId);

        log.info("[StompHandler] - WebSocket Connect");
    }

    private void disconnecting(String userId) {
        kafkaService.notifyOffline(userId);
    }

}
