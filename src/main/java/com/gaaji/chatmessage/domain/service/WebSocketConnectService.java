package com.gaaji.chatmessage.domain.service;

public interface WebSocketConnectService {
    void connect(String sessionId, String userId);
    void disconnect(String sessionId);
    void subscribe(String sessionId, String subscriptionId);
    void unsubscribe(String sessionId, String subscriptionId);
}
