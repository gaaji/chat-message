package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.domain.controller.dto.ChatDto;
import com.gaaji.chatmessage.domain.controller.dto.ChatListDto;
import com.gaaji.chatmessage.domain.controller.dto.ChatListRequestDto;

public interface ChatService {

    void chat(ChatDto chat);

    ChatListDto retrieveChatList(ChatListRequestDto chatListRequestDto);
}
