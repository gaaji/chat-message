package com.gaaji.chatmessage.domain.entity;

import com.gaaji.chatmessage.domain.controller.dto.ChatRequestDto;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Data
@Document(collection = "chats")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Chat {
    private ObjectId id;
    private String roomId;
    private String senderId;
    private String content;
    private Date createdAt;

    public static Chat from(ChatRequestDto chatRequestDto) {
        return Chat.builder()
                .id(ObjectId.get())
                .roomId(chatRequestDto.getRoomId())
                .senderId(chatRequestDto.getSenderId())
                .content(chatRequestDto.getContent())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Chat c = (Chat) o;
        return Objects.equals(this.id, c.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
