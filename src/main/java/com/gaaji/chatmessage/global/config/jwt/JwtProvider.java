package com.gaaji.chatmessage.global.config.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
@PropertySource("classpath:security.properties")
public class JwtProvider {
    @Value("${jwt.secret.key}")
    private String secretKey;
    private final long accessTokenExpirationTime = 1000 * 60 * 60 * 24L;

    @PostConstruct
    protected void init() {
        secretKey = Base64.encodeBase64String(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private String createToken(long expirationTime) {
        Date now = new Date();

        return Jwts.builder()
                .setClaims(Jwts.claims())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
                .compact();
    }

    public String createToken() {
        log.info("[JwtProvider JWT Socket Token] - create token");
        return createToken(accessTokenExpirationTime);
    }

    public Claims validateToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return claims.getBody();
    }
}
