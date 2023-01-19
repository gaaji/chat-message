package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.domain.controller.dto.ChatDto;
import com.gaaji.chatmessage.domain.entity.Chat;
import com.gaaji.chatmessage.domain.repository.ChatRepository;
import com.gaaji.chatmessage.global.constants.ApiConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final SimpMessageSendingOperations template;
    private final ChatRepository chatRepository;
    private final KafkaService kafkaService;

    @Override
    public void chat(ChatDto chatDto) {
        Chat chat = Chat.from(chatDto);

        // 1. DB 저장
        chatRepository.save(chat);

        // 2. 구독자에게 브로드캐스트
        broadcasting(chatDto);

        // 3. API 서버 메시지 전달
        kafkaService.chat(chatDto);

    }

    private void broadcasting(ChatDto chat) {
        template.convertAndSend(ApiConstants.SUBSCRIBE_ENDPOINT + chat.getRoomId(), chat);
    }
}
