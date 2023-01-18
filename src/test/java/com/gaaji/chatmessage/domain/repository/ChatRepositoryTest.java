package com.gaaji.chatmessage.domain.repository;

import com.gaaji.chatmessage.domain.controller.dto.ChatRequest;
import com.gaaji.chatmessage.domain.entity.Chat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class ChatRepositoryTest {

    @Autowired
    ChatRepository chatRepository;

    private static final String roomId = UUID.randomUUID().toString();

    private Chat createChat() {
        ChatRequest request = ChatRequest.builder().roomId(roomId).senderId(UUID.randomUUID().toString()).content("hello! new chat!!").build();
        return Chat.from(request);
    }

    @Test
    @Transactional
    void chat_saved_and_found_and_deleted() {
        // save chat
        Chat newChat = createChat();
        chatRepository.save(newChat);

        // find chat
        Chat findChat = chatRepository.findById(newChat.getId().toString())
                .orElseThrow();
        Assertions.assertNotNull(findChat);
        Assertions.assertEquals(newChat.getId(), findChat.getId());

        // rollback
        chatRepository.delete(findChat);
        boolean isPresent = chatRepository.findById(newChat.getId().toString()).isPresent();
        Assertions.assertFalse(isPresent);
    }

    @Test
    @Transactional
    void find_chat_list_by_room_id() {
        Chat chat_1 = createChat();
        Chat chat_2 = createChat();
        Chat chat_3 = createChat();

        // save
        List<Chat> saveChats = List.of(chat_1, chat_2, chat_3);
        chatRepository.saveAll(saveChats);

        // find
        List<Chat> findChats = chatRepository.findChatsByRoomId(roomId);

        // then
        boolean isEquals = findChats.containsAll(saveChats);
        Assertions.assertTrue(isEquals);

        // rollback
        chatRepository.deleteAll(findChats);
    }

}