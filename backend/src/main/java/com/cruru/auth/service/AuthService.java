package com.cruru.auth.service;

import com.cruru.auth.exception.IllegalCookieException;
import com.cruru.auth.exception.IllegalTokenException;
import com.cruru.auth.security.TokenProvider;
import com.cruru.member.domain.Member;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private static final String EMAIL_CLAIM = "email";
    private static final String ROLE_CLAIM = "role";

    private final TokenProvider tokenProvider;

    public String createToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(EMAIL_CLAIM, member.getEmail());
        claims.put(ROLE_CLAIM, member.getRole().name());

        return tokenProvider.createToken(claims);
    }

    public boolean isTokenValid(String token) {
        try {
            return tokenProvider.isExpired(token);
        } catch (IllegalTokenException e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return extractPayload(token, EMAIL_CLAIM);
    }

    private String extractPayload(String token, String key) {
        try {
            String payload = tokenProvider.extractPayload(token, key);
            if (payload == null) {
                throw new IllegalCookieException();
            }
            return payload;
        } catch (IllegalTokenException e) {
            throw new IllegalCookieException();
        }
    }

    public String extractMemberRole(String token) {
        return extractPayload(token, ROLE_CLAIM);
    }
}
