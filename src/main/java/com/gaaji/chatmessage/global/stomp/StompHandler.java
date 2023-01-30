package com.gaaji.chatmessage.global.stomp;

import com.gaaji.chatmessage.domain.service.WebSocketConnectService;
import com.gaaji.chatmessage.global.constants.StringConstants;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;
    private final WebSocketConnectService webSocketConnectService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        StompCommand command = accessor.getCommand();

        log.info("STOMP COMMAND : {}, Header : {}", command, accessor);

        switch (command) {
            case CONNECT :
                connecting(accessor);
                break;
            case SUBSCRIBE:
                subscribing(accessor);
                break;
            case UNSUBSCRIBE:
                unsubscribing(accessor);
                break;
        }

        return message;
    }

    private void connecting(StompHeaderAccessor accessor) {
        log.info("[StompHandler] - WebSocket Connecting ...");

        // Validate token
        String authorization = accessor.getFirstNativeHeader(StringConstants.HEADER_SOCKET_TOKEN);
        jwtProvider.validateToken(authorization);

        // Handling connect event
        String sessionId = accessor.getSessionId();
        String userId = accessor.getFirstNativeHeader(StringConstants.HEADER_AUTH_ID);
        webSocketConnectService.connect(sessionId, userId);

        log.info("[StompHandler] - WebSocket Connect.");
    }

    private void subscribing(StompHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        String subscriptionId = accessor.getSubscriptionId();

        webSocketConnectService.subscribe(sessionId, subscriptionId);
    }

    private void unsubscribing(StompHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();
        String subscriptionId = accessor.getSubscriptionId();

        webSocketConnectService.unsubscribe(sessionId, subscriptionId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();

        disconnecting(sessionId);
    }

    private void disconnecting(String sessionId) {
        log.info("[StompHandler] - WebSocket Disconnecting ...");

        // Handling disconnect event
        webSocketConnectService.disconnect(sessionId);

        log.info("[StompHandler] - WebSocket Disconnect.");
    }

}
