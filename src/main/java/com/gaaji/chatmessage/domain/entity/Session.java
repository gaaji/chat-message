package com.gaaji.chatmessage.domain.entity;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Data
@Document(collection = "sessions")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Session {
    private ObjectId id;
    private String sessionId;
    private String userId;
    private String subscriptionId;

    public static Session create(String sessionId, String userId) {
        return new Session(
                ObjectId.get(),
                sessionId,
                userId,
                null
        );
    }

    public Session subscribe(String subscriptionId) {
        this.setSubscriptionId(subscriptionId);
        return this;
    }

    public Session unsubscribe() {
        this.setSubscriptionId(null);
        return this;
    }

    public boolean isSubscribing() {
        return !this.subscriptionId.isEmpty() || !this.subscriptionId.isBlank() || this.subscriptionId != null;
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
