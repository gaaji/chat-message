package com.gaaji.chatmessage.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.chatmessage.domain.controller.dto.ChatRequest;
import com.gaaji.chatmessage.domain.controller.dto.kafka.ConnectStatusRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void notifyOnline(String userId) {
        ConnectStatusRequest online = ConnectStatusRequest.ofOnline(userId);
        notifyStatus(online);
    }

    @Override
    public void notifyOffline(String userId) {
        ConnectStatusRequest offline = ConnectStatusRequest.ofOffLine(userId);
        notifyStatus(offline);
    }

    private void notifyStatus(ConnectStatusRequest status) {
        String message = convertValueAsString(status);
        sendMessage("status", message);
    }

    @Override
    public void chat(ChatRequest chat) {
        String message = convertValueAsString(chat);
        sendMessage("chat", message);
    }

    private void sendMessage(String topic, String message) {
        log.info("Send kafka Message: topic - {}, message - {}", topic, message);
        this.kafkaTemplate.send(topic, message);
    }

    private String convertValueAsString(Object value) {
        try {
            String message = new ObjectMapper().writeValueAsString(value);
            return message;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
