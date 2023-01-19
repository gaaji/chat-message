package com.gaaji.chatmessage.domain.entity;


import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Data
@Builder
@Document(collection = "sessions")
public class Session {
    private ObjectId id;
    private String sessionId;
    private String userId;

    private Session() {}
    private Session(ObjectId id, String sessionId, String userId) {
        this.id = id;
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public static Session of(String sessionId, String userId) {
        return Session.builder()
                .id(ObjectId.get())
                .sessionId(sessionId)
                .userId(userId)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Session s = (Session) o;
        return Objects.equals(this.id, s.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
