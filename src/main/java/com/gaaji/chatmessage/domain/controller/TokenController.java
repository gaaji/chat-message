package com.gaaji.chatmessage.domain.controller;

import com.gaaji.chatmessage.domain.service.TokenService;
import com.gaaji.chatmessage.global.constants.ApiConstants;
import com.gaaji.chatmessage.global.constants.StringConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.ENDPOINT_CHAT)
public class TokenController {

    private final TokenService tokenService;

    @GetMapping(ApiConstants.ENDPOINT_CREATE_TOKEN)
    @ResponseStatus(HttpStatus.CREATED)
    public void createToken(HttpServletResponse response) {
        String token = tokenService.createToken();

        response.addHeader(StringConstants.HEADER_SOCKET_TOKEN, token);
    }

}
