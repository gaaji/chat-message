package com.gaaji.chatmessage.domain.entity;


import com.gaaji.chatmessage.domain.controller.dto.SubscriptionDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String destination;
    private String subscriptionRoomId;

    public static Session create(String sessionId, String userId) {
        return new Session(
                ObjectId.get(),
                sessionId,
                userId,
                null, null, null
        );
    }

    public Session subscribe(SubscriptionDto subscriptionDto) {
        this.setSubscriptionId(subscriptionDto.getSubscriptionId());
        this.setSubscriptionRoomId(subscriptionDto.getSubscriptionRoomId());
        this.setDestination(subscriptionDto.getDestination());
        return this;
    }

    public Session unsubscribe() {
        this.setSubscriptionId(null);
        this.setSubscriptionRoomId(null);
        this.setDestination(null);
        return this;
    }

    public boolean isSubscribing() {
        return !this.subscriptionId.isEmpty() || !this.subscriptionId.isBlank() || this.subscriptionId != null
                || !this.destination.isEmpty() || !this.destination.isBlank() || this.destination != null;
    }

    public SubscriptionDto getSubscription() {
        return SubscriptionDto.of(this.subscriptionId, this.destination, this.subscriptionRoomId);
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
