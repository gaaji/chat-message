package com.gaaji.chatmessage.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebSocketConnectServiceImpl implements WebSocketConnectService {

    private static Map<String, String> sessions = new HashMap<>();
    private final KafkaService kafkaService;

    @Override
    public void connect(String sessionId, String userId) {
        sessions.put(sessionId, userId);

        kafkaService.notifyOnline(userId);
    }

    @Override
    public void disconnect(String sessionId) {
        if( sessions.containsKey(sessionId) ) {
            String userId = sessions.remove(sessionId);

            kafkaService.notifyOffline(userId);
        }
    }
}
