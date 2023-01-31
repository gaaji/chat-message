package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.domain.controller.dto.ChatDto;
import com.gaaji.chatmessage.domain.entity.Chat;
import com.gaaji.chatmessage.domain.entity.Session;

import java.util.List;

public interface ChatService {

    void chat(ChatDto chat);

    List<Chat> receiveChatLog(Session roomId);

}
