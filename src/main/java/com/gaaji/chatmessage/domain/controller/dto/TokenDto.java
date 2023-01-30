package com.gaaji.chatmessage.domain.controller.dto;

import com.gaaji.chatmessage.global.constants.StringConstants;
import lombok.*;

@Data
@RequiredArgsConstructor(staticName = "of")
public class TokenDto {
    private final String tokenHeaderName = StringConstants.HEADER_SOCKET_TOKEN;
    private final String token;
}
