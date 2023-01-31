package com.gaaji.chatmessage.global.config;

import com.gaaji.chatmessage.global.constants.ApiConstants;
import com.gaaji.chatmessage.global.stomp.StompErrorHandler;
import com.gaaji.chatmessage.global.stomp.StompHandler;
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
    private final StompErrorHandler stompErrorHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(ApiConstants.WEBSOCKET_ENDPOINT)
                .setAllowedOriginPatterns("*");
        // Heartbeat 고려
        // withSockJS()는 JS 라이브러리인 SockJS를 사용한다는 함수로,
        // WebSocket 요청을 SockJS로 강제한다.
//                .withSockJS();
        registry.setErrorHandler(stompErrorHandler);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(ApiConstants.WEBSOCKET_SUBSCRIBE_ENDPOINT);

        registry.setApplicationDestinationPrefixes(ApiConstants.WEBSOCKET_PUBLISH_ENDPOINT);
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}
