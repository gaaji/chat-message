package com.gaaji.chatmessage.domain.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class ChatListDto {
    private final String roomId;
    private final String userId;
    private final List<ChatDto> chatDtos;
}
