package com.gaaji.chatmessage.domain.repository;

import com.gaaji.chatmessage.domain.entity.Session;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SessionRepository extends MongoRepository<Session, String> {

    Optional<Session> findBySessionId(String sessionId);

    Optional<Session> findBySessionIdAndSubscriptionId(String sessionId, String subscriptionId);

}
