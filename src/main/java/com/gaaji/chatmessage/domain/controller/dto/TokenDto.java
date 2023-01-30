package com.gaaji.chatmessage.domain.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class TokenDto {
    private String tokenHeaderName;
    private String token;
}
