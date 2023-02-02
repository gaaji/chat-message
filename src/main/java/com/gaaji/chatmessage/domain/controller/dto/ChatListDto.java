package com.gaaji.chatmessage.domain.controller.dto;

import com.gaaji.chatmessage.domain.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatListDto {
    private List<ChatResponseDto> chatResponseDtos;

    public static ChatListDto of(List<Chat> chats) {
        return new ChatListDto(chats.stream().map(ChatResponseDto::of).collect(Collectors.toList()));
    }
}
