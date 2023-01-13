package com.gaaji.chatmessage.domain.repository;

import com.gaaji.chatmessage.domain.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String> {

    List<Chat> findChatsByRoomId(String roomId);

}
