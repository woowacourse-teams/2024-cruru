package com.cruru.email.service;

import com.cruru.email.exception.NotVerifiedEmailException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailRedisClient {

    private static final String VERIFICATION_CODE_PREFIX = "email_verification_code:";
    private static final String VERIFIED_EMAIL_PREFIX = "email_verified:";
    private static final int VERIFICATION_CODE_EXPIRATION = 10;
    private static final int VERIFIED_EMAIL_EXPIRATION = 10;

    private final RedisTemplate<String, String> redisTemplate;

    public void saveVerificationCode(String email, String verificationCode) {
        redisTemplate.opsForValue()
                .set(
                        VERIFICATION_CODE_PREFIX + email,
                        verificationCode,
                        VERIFICATION_CODE_EXPIRATION,
                        TimeUnit.MINUTES
                );
    }

    public String getVerificationCode(String email) {
        return redisTemplate.opsForValue().get(VERIFICATION_CODE_PREFIX + email);
    }

    public void saveVerifiedEmail(String email) {
        redisTemplate.opsForValue()
                .set(
                        VERIFIED_EMAIL_PREFIX + email,
                        VerificationStatus.VERIFIED.name(),
                        VERIFIED_EMAIL_EXPIRATION,
                        TimeUnit.MINUTES
                );
    }

    public void verifyEmail(String email) {
        String verifiedStatus = redisTemplate.opsForValue().get(VERIFIED_EMAIL_PREFIX + email);
        VerificationStatus status = VerificationStatus.fromValue(verifiedStatus);
        checkVerification(status);
    }

    private void checkVerification(VerificationStatus status) {
        if (!status.isVerified()) {
            throw new NotVerifiedEmailException();
        }
    }
}
