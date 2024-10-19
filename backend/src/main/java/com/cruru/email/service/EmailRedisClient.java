package com.cruru.email.service;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailRedisClient {

    private static final String REDIS_PREFIX = "email_verification:";
    private static final long VERIFICATION_CODE_EXPIRATION = 10;

    private final RedisTemplate<String, String> redisTemplate;

    public void saveVerificationCode(String email, String verificationCode) {
        redisTemplate.opsForValue()
                .set(REDIS_PREFIX + email, verificationCode, VERIFICATION_CODE_EXPIRATION, TimeUnit.MINUTES);
    }

    public String getVerificationCode(String email) {
        return redisTemplate.opsForValue().get(REDIS_PREFIX + email);
    }
}
