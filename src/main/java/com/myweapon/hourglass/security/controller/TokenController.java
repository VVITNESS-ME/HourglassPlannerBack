package com.myweapon.hourglass.security.controller;

import com.myweapon.hourglass.security.entity.RefreshTokenResponse;
import com.myweapon.hourglass.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final JwtUtil jwtUtil;

    @GetMapping("/refresh/{id}")
    public ResponseEntity<RefreshTokenResponse> getRefresh(@PathVariable("id") Long id) {
        String refreshToken = jwtUtil.createRefreshToken(id, Instant.now());
        RefreshTokenResponse response = new RefreshTokenResponse(refreshToken);
        return ResponseEntity.ok(response);
    }
}