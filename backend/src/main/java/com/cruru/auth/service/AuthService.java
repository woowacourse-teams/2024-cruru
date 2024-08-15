package com.cruru.auth.service;

import com.cruru.auth.exception.IllegalCookieException;
import com.cruru.auth.exception.IllegalTokenException;
import com.cruru.auth.security.TokenProvider;
import com.cruru.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private static final String EMAIL = "email";
    private static final String ROLE = "role";

    private final TokenProvider tokenProvider;

    public String createToken(Member member) {
        return tokenProvider.createToken(member);
    }

    public boolean isTokenValid(String token) {
        try {
            return tokenProvider.isExpired(token);
        } catch (IllegalTokenException e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return extractPayload(token, EMAIL);
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
        return extractPayload(token, ROLE);
    }
}
