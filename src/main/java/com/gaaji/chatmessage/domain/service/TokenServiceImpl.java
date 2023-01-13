package com.gaaji.chatmessage.domain.service;

import com.gaaji.chatmessage.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtProvider jwtProvider;

    @Override
    public String createToken() {
        return jwtProvider.createToken();
    }

}
