package com.gaaji.chatmessage.domain.controller;

import com.gaaji.chatmessage.domain.controller.dto.ChatDto;
import com.gaaji.chatmessage.domain.service.ChatService;
import com.gaaji.chatmessage.global.constants.ApiConstants;
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

    @MessageMapping(ApiConstants.ENDPOINT_CHAT)
    public void chat(@Payload ChatDto chatDto) {
        template.convertAndSend(ApiConstants.SUBSCRIBE_ENDPOINT + chatDto.getRoomId(), chatDto);
        chatService.chat(chatDto);
    }
}
