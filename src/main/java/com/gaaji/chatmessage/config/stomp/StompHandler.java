package com.gaaji.chatmessage.config.stomp;

import com.gaaji.chatmessage.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if(accessor.getCommand() == StompCommand.CONNECT) {
            String authorization = accessor.getFirstNativeHeader("Authorization");
            String token = null;
            if (StringUtils.hasText(authorization) && authorization.contains("Bearer")) {
                token = authorization.replace("Bearer","");
            }
            if(token == null || jwtProvider.validateToken(token) == null) {
                throw new RuntimeException(); // TODO 예외 클래스 정의
            }
        }

        return message;
    }
}
