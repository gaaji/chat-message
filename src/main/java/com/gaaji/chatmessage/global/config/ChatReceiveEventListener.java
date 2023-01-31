package com.gaaji.chatmessage.global.config;

import com.gaaji.chatmessage.domain.entity.Session;
import com.gaaji.chatmessage.domain.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatReceiveEventListener {

    private final ChatService chatService;

    @EventListener
    public void handleEvent(Session session){
        chatService.receiveChatLog(session);

    }

}
