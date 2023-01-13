package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.domain.controller.dto.ChatRequest;
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
    public void chat(ChatRequest chat) {
        chat.setContent(chat.getContent());

        // 1. DB 저장

        // 2. API 서버 전달

        // 3. 구독자에게 브로드캐스트
        broadcasting(chat);
    }

    private void broadcasting(ChatRequest chat) {
        template.convertAndSend(SUB_URL + chat.getRoomId(), chat);
    }
}
