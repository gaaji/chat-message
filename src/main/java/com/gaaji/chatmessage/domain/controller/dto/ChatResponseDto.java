package com.gaaji.chatmessage.domain.controller.dto;

import com.gaaji.chatmessage.domain.entity.Chat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatResponseDto {
    private String messageId;
    private String roomId;
    private String senderId;
    private String content;
    private LocalDateTime chattedAt;

    public static ChatResponseDto of(Chat entity) {
        return new ChatResponseDto(
                entity.getId().toString(),
                entity.getRoomId(),
                entity.getSenderId(),
                entity.getContent(),
                entity.getCreatedAt()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
        );
    }
}
