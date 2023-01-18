package com.gaaji.chatmessage.domain.controller.dto.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectStatusRequest {
    private static final String ONLINE = "ONLINE";
    private static final String OFFLINE = "OFFLINE";
    private String userId;
    private String status;

    public ConnectStatusRequest setStatus(String status) {
        this.status = status;
        return this;
    }

    private static ConnectStatusRequest of(String userId) {
        return ConnectStatusRequest.builder().userId(userId).build();
    }

    public static ConnectStatusRequest ofOnline(String userId) {
        return of(userId).setStatus(ONLINE);
    }

    public static ConnectStatusRequest ofOffLine(String userId) {
        return of(userId).setStatus(OFFLINE);
    }
}
