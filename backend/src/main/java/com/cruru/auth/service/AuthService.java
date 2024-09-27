package com.cruru.auth.service;

import com.cruru.auth.exception.IllegalTokenException;
import com.cruru.auth.security.PasswordValidator;
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
    private final PasswordValidator passwordValidator;

    public String createToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(EMAIL_CLAIM, member.getEmail());
        claims.put(ROLE_CLAIM, member.getRole().name());

        return tokenProvider.createToken(claims);
    }

    public boolean isTokenValid(String token) {
        try {
            return tokenProvider.isAlive(token);
        } catch (IllegalTokenException e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return extractClaim(token, EMAIL_CLAIM);
    }

    private String extractClaim(String token, String key) {
        String claim = tokenProvider.extractClaim(token, key);
        if (claim == null) {
            throw new IllegalTokenException();
        }
        return claim;
    }

    public String extractMemberRole(String token) {
        return extractClaim(token, ROLE_CLAIM);
    }

    public boolean isNotVerifiedPassword(String rawPassword, String encodedPassword) {
        return !passwordValidator.matches(rawPassword, encodedPassword);
    }
}
