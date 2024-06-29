package com.myweapon.hourglass.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.myweapon.hourglass.security.entity.Token;
import com.myweapon.hourglass.security.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final TokenRepository tokenRepository;

    @Value("${spring.jwt.secret}")
    private String secret;
    @Value("${spring.jwt.token.refresh-expiration-seconds}")
    private int refreshTokenExpirationSeconds;

    // Create a refresh token and save it in the Redis repository
    public String createRefreshToken(Long id, Instant issuedAt) {
        String refreshToken = JWT.create()
                .withClaim("id", id) // Set the user ID as a claim
                .withIssuedAt(issuedAt) // Set the issued time
                .withExpiresAt(issuedAt.plusSeconds(refreshTokenExpirationSeconds)) // Set the expiration time
                .sign(Algorithm.HMAC512(secret)); // Sign the token with the secret key

        Token token = new Token(id, refreshToken, 3600L);
        tokenRepository.save(token);
        return refreshToken;
    }
}
