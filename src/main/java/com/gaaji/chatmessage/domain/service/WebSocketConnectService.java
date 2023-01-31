package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.domain.controller.dto.SubscriptionDto;
import org.springframework.security.core.Authentication;

public interface WebSocketConnectService {
    Authentication connect(String sessionId, String userId);
    void disconnect(String sessionId);
    void subscribe(String sessionId, SubscriptionDto subscriptionDto);
    void unsubscribe(String sessionId, String subscriptionId);
}
