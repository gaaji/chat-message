package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.domain.controller.dto.ChatRequestDto;
import com.gaaji.chatmessage.domain.controller.dto.ChatListDto;
import com.gaaji.chatmessage.domain.controller.dto.ChatListRequestDto;
import com.gaaji.chatmessage.domain.entity.Chat;
import com.gaaji.chatmessage.domain.repository.ChatRepository;
import com.gaaji.chatmessage.global.constants.IntegerConstants;
import com.gaaji.chatmessage.global.exception.ChatMessageException;
import com.gaaji.chatmessage.global.exception.ErrorCode;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final KafkaService kafkaService;

    private final ExecutorService kafkaConnectThreadPool =
            Executors.newFixedThreadPool(IntegerConstants.RUNTIME_CORE_COUNT,
                    new ThreadFactoryBuilder().setNameFormat("Gaaji-Chat-Kafka-Connect-Thread-%d").build());

    private final ExecutorService databaseConnectThreadPool =
            Executors.newFixedThreadPool(IntegerConstants.RUNTIME_CORE_COUNT,
                    new ThreadFactoryBuilder().setNameFormat("Gaaji-Chat-Database-Connect-Thread-%d").build());

    @Override
    public void chat(ChatRequestDto chatRequestDto) {
        // Kafka 메시지 발행
        kafkaConnectThreadPool.submit(() -> kafkaService.chat(chatRequestDto));

        // DB 저장
        databaseConnectThreadPool.submit(() -> {
            Chat chat = Chat.from(chatRequestDto);
            chatRepository.save(chat);
        });
    }

    @Override
    public ChatListDto retrieveChatList(ChatListRequestDto chatListRequestDto) {
        String roomId = chatListRequestDto.getRoomId();
        String lastMessageId = chatListRequestDto.getLastMessageId();
        PageRequest pageRequest = PageRequest.of(0, 10);

        List<Chat> chats;
        if(Objects.equals(lastMessageId, "-1")) {
            chats = chatRepository.findThreeChatsByRoomId(roomId, pageRequest);
        } else {
            try {
                ObjectId objectId = new ObjectId(lastMessageId);
                chats = chatRepository.findThreeChatsByRoomIdAndIdLessThan(roomId, objectId, pageRequest);
            } catch (IllegalArgumentException e) {
                throw new ChatMessageException(ErrorCode.JSON_PROCESSING_ERROR);
            }
        }
        assert chats != null;

        return ChatListDto.of(chats);
    }

}
