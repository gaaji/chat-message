package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.domain.controller.dto.ChatRequest;
import com.gaaji.chatmessage.domain.entity.Chat;
import com.gaaji.chatmessage.domain.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private static final String SUB_URL = "/sub/chat/room/";
    private final SimpMessageSendingOperations template;
    private final ChatRepository chatRepository;

    @Override
    public void chat(ChatRequest chatRequest) {
        Chat chat = Chat.of(chatRequest);

        // 1. DB 저장
        chatRepository.save(chat);

        // 2. 구독자에게 브로드캐스트
        broadcasting(chatRequest);

        // 3. API 서버 전달

    }

    private void broadcasting(ChatRequest chat) {
        template.convertAndSend(SUB_URL + chat.getRoomId(), chat);
    }
}
