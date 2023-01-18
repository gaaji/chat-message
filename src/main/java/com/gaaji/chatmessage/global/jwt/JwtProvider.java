package com.gaaji.chatmessage.global.jwt;

import com.gaaji.chatmessage.global.constants.StringConstants;
import com.gaaji.chatmessage.global.exception.ExceptionHandlerConstants;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
    private static final long EXPIRATION_SECOND = 300; // 5 min.

    @PostConstruct
    protected void init() {
        secretKey = Base64.encodeBase64String(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private String createToken(long expirationSecond) {
        Date now = new Date();

        return Jwts.builder()
                .setClaims(Jwts.claims())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ( expirationSecond * 1000L )))
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 세팅
                .compact();
    }

    public String createToken() {
        log.info("[JwtProvider] - Token Creating ...");
        return StringConstants.BEARER_PREFIX + createToken(EXPIRATION_SECOND);
    }

    public void validateToken(String inputToken) {
        log.info("[JwtProvider] - Token Validating ...");

        String token;
        token = validateTokenIsNonNull(inputToken);
        token = validateTokenHasPrefix(token);

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            validateTokenInvalidated(claims);

            log.info("[JwtProvider] - Token Validated.");

        } catch (MalformedJwtException e ) {
            throw new MessageDeliveryException(ExceptionHandlerConstants.JWT_MALFORMED);

        } catch (ExpiredJwtException e) {
            throw new MessageDeliveryException(ExceptionHandlerConstants.JWT_EXPIRED);
        }
    }

    private String validateTokenIsNonNull(String token) {
        if(!StringUtils.hasText(token)) {
            throw new MessageDeliveryException(ExceptionHandlerConstants.JWT_NULL);
        }
        return token;
    }

    private String validateTokenHasPrefix(String token) {
        if (!token.contains(StringConstants.BEARER_PREFIX)) {
            throw new MessageDeliveryException(ExceptionHandlerConstants.JWT_INVALIDATED_PREFIX);
        }
        return token.substring(StringConstants.BEARER_PREFIX.length());
    }

    private void validateTokenInvalidated(Jws<Claims> claims) {
        if (claims.getBody() == null) {
            throw new MessageDeliveryException(ExceptionHandlerConstants.JWT_INVALIDATED);
        }
    }

}
