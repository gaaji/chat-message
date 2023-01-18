package com.gaaji.chatmessage.domain.controller;

import com.gaaji.chatmessage.domain.controller.dto.ChatRequest;
import com.gaaji.chatmessage.domain.service.ChatService;
import com.gaaji.chatmessage.global.constants.ApiConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping(ApiConstants.ENDPOINT_CHAT)
    public void chat(@Payload ChatRequest chat) {
        log.info("CHAT: {}", chat);

        chatService.chat(chat);
    }
}
