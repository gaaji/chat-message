package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.domain.controller.dto.SubscriptionDto;

public interface WebSocketConnectService {
    void connect(String sessionId, String userId);
    void disconnect(String sessionId);
    void subscribe(String sessionId, SubscriptionDto subscriptionDto);
    void unsubscribe(String sessionId, String subscriptionId);
}
