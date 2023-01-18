package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.domain.controller.dto.ChatRequest;

public interface KafkaService {
    void notifyOnline(String userId);
    void notifyOffline(String userId);
    void chat(ChatRequest chat);
}
