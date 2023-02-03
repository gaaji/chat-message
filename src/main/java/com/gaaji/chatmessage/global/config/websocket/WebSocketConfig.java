package com.gaaji.chatmessage.global.config.websocket;

import com.gaaji.chatmessage.global.constants.StompConstants;
import com.gaaji.chatmessage.global.config.websocket.stomp.StompErrorHandler;
import com.gaaji.chatmessage.global.config.websocket.stomp.StompInboundChannelInterceptor;
import com.gaaji.chatmessage.global.config.websocket.stomp.StompHandshakeHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompInboundChannelInterceptor stompInboundChannelInterceptor;
    private final StompHandshakeHandler stompHandshakeHandler;
    private final StompErrorHandler stompErrorHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.setErrorHandler(stompErrorHandler)
                .addEndpoint(StompConstants.MAIN_ENDPOINT)
                .setHandshakeHandler(stompHandshakeHandler)
                .setAllowedOriginPatterns("*");
//                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(StompConstants.SUBSCRIBE_PREFIX_TOPIC, StompConstants.SUBSCRIBE_PREFIX_QUEUE);

        registry.setApplicationDestinationPrefixes(StompConstants.PUBLISH_PREFIX_APP);

        registry.setUserDestinationPrefix(StompConstants.SUBSCRIBE_PREFIX_USER);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompInboundChannelInterceptor);
    }

}
