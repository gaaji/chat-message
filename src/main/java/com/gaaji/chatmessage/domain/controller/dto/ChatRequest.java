package com.gaaji.chatmessage.domain.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRequest {
    private String roomId;
    private String senderId;
    private String content;
}
