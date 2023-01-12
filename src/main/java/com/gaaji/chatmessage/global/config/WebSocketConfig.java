package com.gaaji.chatmessage.global.config;

import com.gaaji.chatmessage.global.config.stomp.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    /** Websocket heartbeat time */
    private static final long HEARTBEAT_TIME = 10 * 1000L;
    /** Websocket disconnect delay time */
    private static final long DISCONNECT_DELAY_TIME = 60 * 1000L;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-gaaji-chat")
                .setAllowedOrigins("*")
                .withSockJS()
                .setHeartbeatTime(HEARTBEAT_TIME)
                .setDisconnectDelay(DISCONNECT_DELAY_TIME);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지 구독 요청 url => 메시지를 받을 때
        registry.enableSimpleBroker("/sub");
        // 메시지 발행 요청 url => 메시지를 보낼 때
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}
