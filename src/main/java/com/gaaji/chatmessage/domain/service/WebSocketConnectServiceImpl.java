package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.domain.controller.dto.SubscriptionDto;
import com.gaaji.chatmessage.domain.entity.Session;
import com.gaaji.chatmessage.domain.repository.SessionRepository;
import com.gaaji.chatmessage.global.constants.IntegerConstants;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketConnectServiceImpl implements WebSocketConnectService {

    private final SessionRepository sessionRepository;
    private final KafkaService kafkaService;
    private final ChatService chatService;

    private final ExecutorService kafkaConnectThreadPool =
            Executors.newFixedThreadPool(IntegerConstants.RUNTIME_CORE_COUNT,
                    new ThreadFactoryBuilder().setNameFormat("Gaaji-Connect-Kafka-Connect-Thread-%d").build());


    @Override
    public Authentication connect(String sessionId, String userId) {
        if(sessionId.isBlank() || userId.isBlank()) {
            log.error("cannot connect - sessionId : {}, userId : {}", sessionId, userId);
        }
        Session connectSession = Session.create(sessionId, userId);
        sessionRepository.save(connectSession);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(userId, sessionId, authorities);
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
    public void subscribe(String sessionId, SubscriptionDto subscriptionDto) {
        Session session = sessionRepository.findBySessionId(sessionId).orElseThrow();

        session.subscribe(subscriptionDto);
        sessionRepository.save(session);

        chatService.receiveChatLog(session);

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
