package com.gaaji.chatmessage.domain.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRequestDto {
    private String roomId;
    private String senderId;
    private String content;

    public static ChatRequestDto of(String roomId, String senderId, String content) {
        return new ChatRequestDto(roomId, senderId, content);
    }

}
