package com.gaaji.chatmessage.domain.controller.dto;

import lombok.Data;

@Data
public class ChatListRequestDto {
    private String roomId;
    private String lastMessageId;
}
