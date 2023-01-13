package com.gaaji.chatmessage.domain.entity;

import com.gaaji.chatmessage.domain.controller.dto.ChatRequest;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Data
@Builder
@Document(collection = "chats")
public class Chat {
    private ObjectId id;
    private String roomId;
    private String senderId;
    private String content;
    private Date createdAt;

    public static Chat of(ChatRequest chatDto) {
        return Chat.builder()
                .id(ObjectId.get())
                .roomId(chatDto.getRoomId())
                .senderId(chatDto.getSenderId())
                .content(chatDto.getContent())
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
