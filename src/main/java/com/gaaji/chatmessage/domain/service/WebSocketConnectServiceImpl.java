package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.domain.entity.Session;
import com.gaaji.chatmessage.domain.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketConnectServiceImpl implements WebSocketConnectService {

    private final SessionRepository sessionRepository;
    private final KafkaService kafkaService;

    @Override
    public void connect(String sessionId, String userId) {
        Session connectSession = Session.of(sessionId, userId);

        sessionRepository.save(connectSession);

        kafkaService.notifyOnline(userId);
    }

    @Override
    public void disconnect(String sessionId) {
        sessionRepository.findBySessionId(sessionId)
                .ifPresent(
                        session -> {
                            kafkaService.notifyOffline(session.getUserId());
                            sessionRepository.delete(session);
                        }
                );
    }
}
