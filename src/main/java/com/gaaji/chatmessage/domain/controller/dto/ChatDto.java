package com.gaaji.chatmessage.domain.controller.dto;

import com.gaaji.chatmessage.domain.entity.Chat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatDto {
    private String roomId;
    private String senderId;
    private String content;

    public static ChatDto of(String roomId, String senderId, String content) {
        return new ChatDto(roomId, senderId, content);
    }

    public static ChatDto of(Chat entity) {
        return new ChatDto(entity.getRoomId(), entity.getSenderId(), entity.getContent());
    }
}
