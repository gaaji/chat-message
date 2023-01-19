package com.gaaji.chatmessage.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.chatmessage.domain.controller.dto.ChatDto;
import com.gaaji.chatmessage.domain.controller.dto.ConnectUserDto;
import com.gaaji.chatmessage.global.constants.StringConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void notifyOnline(String userId) {
        ConnectUserDto online = ConnectUserDto.from(userId);

        String message = convertValueAsString(online);

        sendMessage(StringConstants.KAFKA_TOPIC_CONNECTED, message);
    }

    @Override
    public void notifyOffline(String userId) {
        ConnectUserDto offline = ConnectUserDto.from(userId);

        String message = convertValueAsString(offline);

        sendMessage(StringConstants.KAFKA_TOPIC_DISCONNECTED, message);
    }

    @Override
    public void chat(ChatDto chat) {
        String message = convertValueAsString(chat);

        sendMessage(StringConstants.KAFKA_TOPIC_CHATTED, message);
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
