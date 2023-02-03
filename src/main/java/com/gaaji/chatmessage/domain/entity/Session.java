package com.gaaji.chatmessage.domain.entity;


import com.gaaji.chatmessage.domain.controller.dto.SubscriptionDto;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Data
@Document(collection = "sessions")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Session {
    private ObjectId id;
    private String sessionId;
    private String userId;
    private String subscriptionId;
    private String destination;
    private Date connectedAt;

    public static Session create(String sessionId, String userId) {
        return Session.builder()
                .id(ObjectId.get())
                .sessionId(sessionId)
                .userId(userId)
                .subscriptionId(null)
                .destination(null)
                .build();
    }

    public Session subscribe(SubscriptionDto dto) {
        this.subscriptionId = dto.getSubscriptionId();
        this.destination = dto.getDestination();
        return this;
    }

    public Session unsubscribe() {
        this.subscriptionId = null;
        this.destination = null;
        return this;
    }

    public boolean isSubscribing() {
        return !this.subscriptionId.isEmpty() || !this.subscriptionId.isBlank() || this.subscriptionId != null
                || !this.destination.isEmpty() || !this.destination.isBlank() || this.destination != null;
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
