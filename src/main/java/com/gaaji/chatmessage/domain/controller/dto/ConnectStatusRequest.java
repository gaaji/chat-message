package com.gaaji.chatmessage.domain.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectStatusRequest {
    private static final String ONLINE = "ONLINE";
    private static final String OFFLINE = "OFFLINE";
    private String userId;
    private String status;

    private ConnectStatusRequest setStatus(String status) {
        this.status = status;
        return this;
    }

    private static ConnectStatusRequest from(String userId) {
        return ConnectStatusRequest.builder().userId(userId).build();
    }

    public static ConnectStatusRequest newOnline(String userId) {
        return ConnectStatusRequest.from(userId).setStatus(ONLINE);
    }

    public static ConnectStatusRequest newOffline(String userId) {
        return ConnectStatusRequest.from(userId).setStatus(OFFLINE);
    }
}
