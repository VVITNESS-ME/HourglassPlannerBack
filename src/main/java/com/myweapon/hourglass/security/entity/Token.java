package com.myweapon.hourglass.security.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

// @RedisHash: Redis에 저장될 객체 이름을 지정
@RedisHash("token")
@AllArgsConstructor
@Getter
public class Token {
    @Id
    private Long id;
    private String refreshToken;
    @TimeToLive
    private Long expirationTimeSeconds;
}