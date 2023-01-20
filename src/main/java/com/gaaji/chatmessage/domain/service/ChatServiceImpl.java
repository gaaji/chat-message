package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.domain.controller.dto.ChatDto;
import com.gaaji.chatmessage.domain.entity.Chat;
import com.gaaji.chatmessage.domain.repository.ChatRepository;
import com.gaaji.chatmessage.global.constants.IntegerConstants;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final ChatRepository chatRepository;
    private final KafkaService kafkaService;
    private final ExecutorService kafkaConnectThreadPool =
            Executors.newFixedThreadPool(IntegerConstants.RUNTIME_CORE_COUNT,
                    new ThreadFactoryBuilder().setNameFormat("Gaaji-Chat-Kafka-Connect-Thread-%d").build());

    private final ExecutorService databaseConnectThreadPool =
            Executors.newFixedThreadPool(IntegerConstants.RUNTIME_CORE_COUNT,
                    new ThreadFactoryBuilder().setNameFormat("Gaaji-Chat-Database-Connect-Thread-%d").build());

    @Override
    public void chat(ChatDto chatDto) {
        // Kafka 메시지 발행
        kafkaConnectThreadPool.submit(() -> kafkaService.chat(chatDto));

        // DB 저장
        databaseConnectThreadPool.submit(() -> {
            Chat chat = Chat.from(chatDto);
            chatRepository.save(chat);
        });
    }
}
