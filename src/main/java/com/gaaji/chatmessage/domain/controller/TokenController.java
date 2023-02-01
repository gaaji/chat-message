package com.gaaji.chatmessage.domain.controller;

import com.gaaji.chatmessage.domain.controller.dto.TokenDto;
import com.gaaji.chatmessage.domain.service.TokenService;
import com.gaaji.chatmessage.global.constants.HttpConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @GetMapping(HttpConstants.ENDPOINT_GET_TOKEN)
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDto createToken() {
        String token = tokenService.createToken();

        return TokenDto.of(token);
    }

}
