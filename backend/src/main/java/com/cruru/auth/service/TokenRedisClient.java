package com.cruru.auth.service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenRedisClient {

    private static final String TOKEN_PREFIX = "token:";
    private static final int TOKEN_EXPIRATION = 10;

    private final RedisTemplate<String, String> redisTemplate;

    public void saveToken(String email, String token) {
        redisTemplate.opsForValue()
                .set(
                        TOKEN_PREFIX + email,
                        token,
                        TOKEN_EXPIRATION,
                        TimeUnit.MINUTES
                );
    }

    public Optional<String> getToken(String email) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(TOKEN_PREFIX + email));
    }

    public boolean existsByEmail(String email) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(TOKEN_PREFIX + email));
    }

    public boolean existsByToken(String email, String token) {
        return getToken(email)
                .map(storedToken -> storedToken.equals(token))
                .orElse(false);
    }
}
