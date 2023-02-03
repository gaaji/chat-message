package com.gaaji.chatmessage.domain.repository;

import com.gaaji.chatmessage.domain.entity.Chat;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String> {

    List<Chat> findChatsByRoomId(String roomId);

    @Query(sort = "{'_id' : -1 }")
    List<Chat> findTenChatsByRoomId(String roomId, Pageable pageable);

    @Query(sort = "{'_id' : -1}")
    List<Chat> findTenChatsByRoomIdAndIdLessThan(String roomId, ObjectId id, Pageable pageable);


}
