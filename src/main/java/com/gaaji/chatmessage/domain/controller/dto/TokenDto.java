package com.gaaji.chatmessage.domain.controller.dto;

import com.gaaji.chatmessage.global.constants.StringConstants;
import lombok.*;

@Data
@RequiredArgsConstructor(staticName = "of")
public class TokenDto {
    private final String tokenHeaderName = StringConstants.HEADER_SOCKET_TOKEN;
    private final String token;

//    public static TokenDto of(String token) {
//        return new TokenDto(StringConstants.HEADER_SOCKET_TOKEN, token);
//    }
}
