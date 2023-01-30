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
        Session connectSession = Session.create(sessionId, userId);
        sessionRepository.save(connectSession);
    }

    @Override
    public void disconnect(String sessionId) {
        sessionRepository.findBySessionId(sessionId)
                .ifPresent(session -> {
                    if(session.isSubscribing()) {
                        unsubscribe(session.getSessionId(), session.getSubscriptionId());
                    }
                    sessionRepository.delete(session);
                });
    }

    @Override
    public void subscribe(String sessionId, String subscriptionId) {
        Session session = sessionRepository.findBySessionId(sessionId).orElseThrow();
        session.subscribe(subscriptionId);
        sessionRepository.save(session);

        String userId = session.getUserId();
        kafkaConnectThreadPool.submit(() -> kafkaService.notifySubscribe(userId));
    }

    @Override
    public void unsubscribe(String sessionId, String subscriptionId) {
        Session session = sessionRepository.findBySessionIdAndSubscriptionId(sessionId, subscriptionId).orElseThrow();
        session.unsubscribe();
        sessionRepository.save(session);

        String userId = session.getUserId();
        kafkaConnectThreadPool.submit(() -> kafkaService.notifyUnsubscribe(userId));
    }

}
