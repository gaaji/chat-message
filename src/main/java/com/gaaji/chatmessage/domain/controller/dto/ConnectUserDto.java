package com.gaaji.chatmessage.domain.controller.dto;

import lombok.Data;

@Data
public class ConnectUserDto {
    private String userId;

    private ConnectUserDto() {}

    private ConnectUserDto(String userId) {
        this.userId = userId;
    }

    public static ConnectUserDto from(String userId) {
        return new ConnectUserDto(userId);
    }

}
