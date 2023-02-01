package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.domain.controller.dto.ChatDto;
import com.gaaji.chatmessage.domain.controller.dto.ChatListDto;
import com.gaaji.chatmessage.domain.controller.dto.ChatListRequestDto;
import com.gaaji.chatmessage.domain.entity.Chat;
import com.gaaji.chatmessage.domain.repository.ChatRepository;
import com.gaaji.chatmessage.global.constants.StompConstants;
import com.gaaji.chatmessage.global.constants.IntegerConstants;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final SimpMessagingTemplate template;
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
        template.convertAndSend(StompConstants.ENDPOINT_TOPIC_CHAT_ROOM + chatDto.getRoomId(), chatDto);

        // Kafka 메시지 발행
        kafkaConnectThreadPool.submit(() -> kafkaService.chat(chatDto));

        // DB 저장
        databaseConnectThreadPool.submit(() -> {
            Chat chat = Chat.from(chatDto);
            chatRepository.save(chat);
        });
    }

    @Override
    public ChatListDto retrieveChatList(Principal principal, ChatListRequestDto chatListRequestDto) {
        List<Chat> chats = chatRepository.findChatsByRoomId(chatListRequestDto.getRoomId());
        return ChatListDto.of(chats);
    }

}
