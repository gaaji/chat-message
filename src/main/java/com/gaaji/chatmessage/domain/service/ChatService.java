package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.domain.controller.dto.ChatDto;
import com.gaaji.chatmessage.domain.controller.dto.ChatListDto;
import com.gaaji.chatmessage.domain.controller.dto.ChatListRequestDto;

import java.security.Principal;

public interface ChatService {

    void chat(ChatDto chat);

    ChatListDto retrieveChatList(Principal principal, ChatListRequestDto chatListRequestDto);
}
