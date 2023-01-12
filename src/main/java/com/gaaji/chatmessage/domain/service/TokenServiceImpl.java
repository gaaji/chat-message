package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.global.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtProvider jwtProvider;

    @Override
    public String createToken() {
        return jwtProvider.createToken();
    }

}
