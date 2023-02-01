package com.gaaji.chatmessage.global.stomp;

import java.security.Principal;
import java.util.UUID;

public class StompUserPrincipal implements Principal {
    private String name;

    public StompUserPrincipal() {
        this.name = UUID.randomUUID().toString();
    }

    public StompUserPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
