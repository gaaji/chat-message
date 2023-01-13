package com.gaaji.chatmessage.domain.controller;

import com.gaaji.chatmessage.domain.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class TokenController {

    private final TokenService tokenService;

    @GetMapping("/token")
    @ResponseStatus(HttpStatus.CREATED)
    public void createToken(HttpServletResponse response) {
        String token = tokenService.createToken();

        response.addHeader("WebSocketToken", token);
    }

}
