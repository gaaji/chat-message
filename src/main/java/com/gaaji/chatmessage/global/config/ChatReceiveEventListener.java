package com.gaaji.chatmessage.global.config;

import com.gaaji.chatmessage.domain.entity.Session;
import com.gaaji.chatmessage.domain.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatReceiveEventListener {

    private final ChatService chatService;

    @EventListener
    public void handleEvent(Session session){
        log.info("event: {}", session);
        chatService.receiveChatLog(session);

    }

}