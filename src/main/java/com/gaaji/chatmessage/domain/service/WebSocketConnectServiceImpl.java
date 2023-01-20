package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.domain.entity.Session;
import com.gaaji.chatmessage.domain.repository.SessionRepository;
import com.gaaji.chatmessage.global.constants.IntegerConstants;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class WebSocketConnectServiceImpl implements WebSocketConnectService {

    private final SessionRepository sessionRepository;
    private final KafkaService kafkaService;

    private final ExecutorService kafkaConnectThreadPool =
            Executors.newFixedThreadPool(IntegerConstants.RUNTIME_CORE_COUNT,
                    new ThreadFactoryBuilder().setNameFormat("Gaaji-Connect-Kafka-Connect-Thread-%d").build());


    @Override
    public void connect(String sessionId, String userId) {
        Session connectSession = Session.of(sessionId, userId);
        sessionRepository.save(connectSession);

        kafkaConnectThreadPool.submit(() ->
                kafkaService.notifyOnline(connectSession.getUserId()));
    }

    @Override
    public void disconnect(String sessionId) {
        sessionRepository.findBySessionId(sessionId)
                .ifPresent(
                        session -> {
                            sessionRepository.delete(session);

                            kafkaConnectThreadPool.submit(() ->
                                    kafkaService.notifyOffline(session.getUserId()));
                        }
                );
    }
}
