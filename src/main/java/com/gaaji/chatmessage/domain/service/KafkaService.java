package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.domain.controller.dto.ChatRequestDto;

public interface KafkaService {
    void notifySubscribe(String userId);
    void notifyUnsubscribe(String userId);
    void chat(ChatRequestDto chat);
}
