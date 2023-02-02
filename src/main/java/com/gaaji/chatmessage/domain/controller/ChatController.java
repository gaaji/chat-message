package com.gaaji.chatmessage.domain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.chatmessage.domain.controller.dto.ChatRequestDto;
import com.gaaji.chatmessage.domain.controller.dto.ChatListDto;
import com.gaaji.chatmessage.domain.controller.dto.ChatListRequestDto;
import com.gaaji.chatmessage.domain.service.ChatService;
import com.gaaji.chatmessage.global.constants.StompConstants;
import com.gaaji.chatmessage.global.exception.ChatMessageException;
import com.gaaji.chatmessage.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template;
    private final ChatService chatService;
    private final ObjectMapper objectMapper;

    @MessageMapping(StompConstants.ENDPOINT_APP_CHAT)
    public void chat(@Payload ChatRequestDto chatRequestDto) {
        template.convertAndSend(StompConstants.ENDPOINT_TOPIC_CHAT_ROOM + chatRequestDto.getRoomId(), chatRequestDto);

        chatService.chat(chatRequestDto);
    }

    @MessageMapping(StompConstants.ENDPOINT_APP_CHAT_LIST)
    @SendToUser(StompConstants.ENDPOINT_QUEUE_CHAT_LIST)
    public String retrieveChatList(@Payload ChatListRequestDto chatListRequestDto) {
        try {
            ChatListDto chatListDto = chatService.retrieveChatList(chatListRequestDto);
            log.info("Chat List Dto : {}", chatListDto);

            return objectMapper.writeValueAsString(chatListDto);

        } catch (JsonProcessingException e) {
            throw new ChatMessageException(ErrorCode.JSON_PROCESSING_ERROR);
        }
    }
}
