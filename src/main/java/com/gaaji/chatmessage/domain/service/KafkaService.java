package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.domain.controller.dto.ChatDto;

public interface KafkaService {
    void notifyOnline(String userId);
    void notifyOffline(String userId);
    void chat(ChatDto chat);
}
