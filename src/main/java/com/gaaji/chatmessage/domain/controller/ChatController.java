package com.gaaji.chatmessage.domain.controller;

import com.gaaji.chatmessage.domain.controller.dto.ChatRequest;
import com.gaaji.chatmessage.domain.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations template;
    private final ChatService chatService;

    private static final String SUB_URL = "/sub/chat/room/";

    @MessageMapping("/chats")
    public void chat(@Payload ChatRequest chat) {
        log.info("CHAT: {}", chat);

        chat.setContent(chat.getContent());

        // 1. DB 저장

        // 2. API 서버 전달

        // 3. 구독자에게 브로드캐스트
        template.convertAndSend(SUB_URL + chat.getRoomId(), chat);
    }
}
