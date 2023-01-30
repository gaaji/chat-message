package com.gaaji.chatmessage.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.chatmessage.domain.controller.dto.ChatDto;
import com.gaaji.chatmessage.domain.controller.dto.UserIdDto;
import com.gaaji.chatmessage.global.constants.StringConstants;
import com.gaaji.chatmessage.global.exception.ChatMessageException;
import com.gaaji.chatmessage.global.exception.ErrorCode;
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
    public void notifySubscribe(String userId) {
        UserIdDto userIdDto = UserIdDto.of(userId);

        String message = convertValueAsString(userIdDto);

        sendMessage(StringConstants.TOPIC_MEMBER_SUBSCRIBED, message);
    }

    @Override
    public void notifyUnsubscribe(String userId) {
        UserIdDto userIdDto = UserIdDto.of(userId);

        String message = convertValueAsString(userIdDto);

        sendMessage(StringConstants.TOPIC_MEMBER_UNSUBSCRIBED, message);
    }

    @Override
    public void chat(ChatDto chat) {
        String message = convertValueAsString(chat);

        sendMessage(StringConstants.TOPIC_CHATTED, message);
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
            throw new ChatMessageException(ErrorCode.JSON_PROCESSING_ERROR);
        }
    }

}
