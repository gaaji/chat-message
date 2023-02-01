package com.gaaji.chatmessage.global.config;

import com.gaaji.chatmessage.global.constants.StompConstants;
import com.gaaji.chatmessage.global.stomp.StompErrorHandler;
import com.gaaji.chatmessage.global.stomp.StompHandler;
import com.gaaji.chatmessage.global.stomp.StompHandshakeHandler;
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

    private final StompHandler stompHandler;
    private final StompErrorHandler stompErrorHandler;
    private final StompHandshakeHandler stompHandshakeHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(StompConstants.MAIN_ENDPOINT)
                .setHandshakeHandler(stompHandshakeHandler)
                .setAllowedOriginPatterns("*");
        // Heartbeat 고려
        // withSockJS()는 JS 라이브러리인 SockJS를 사용한다는 함수로,
        // WebSocket 요청을 SockJS로 강제한다.
//                .withSockJS();
        registry.setErrorHandler(stompErrorHandler);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(StompConstants.SUBSCRIBE_PREFIX_TOPIC, StompConstants.SUBSCRIBE_PREFIX_QUEUE);

        registry.setApplicationDestinationPrefixes(StompConstants.PUBLISH_PREFIX_APP);

        registry.setUserDestinationPrefix(StompConstants.SUBSCRIBE_PREFIX_USER);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }

}
