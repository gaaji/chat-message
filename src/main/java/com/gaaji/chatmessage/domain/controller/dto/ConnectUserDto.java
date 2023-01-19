package com.gaaji.chatmessage.domain.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectUserDto {
    private String userId;

    private static ConnectUserDto from(String userId) {
        return new ConnectUserDto(userId);
    }

}
