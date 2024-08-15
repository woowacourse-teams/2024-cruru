package com.cruru.auth.service;

import com.cruru.auth.exception.IllegalCookieException;
import com.cruru.auth.exception.IllegalTokenException;
import com.cruru.auth.security.JwtTokenProvider;
import com.cruru.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public String createToken(Member member) {
        return jwtTokenProvider.create(member);
    }

    public boolean isLoginMember(String token) {
        return !jwtTokenProvider.isExpired(token) || jwtTokenProvider.extractMemberEmail(token) != null;
    }

    public String extractEmailPayload(String token) {
        try {
            String extractedEmail = jwtTokenProvider.extractMemberEmail(token);
            if (extractedEmail == null) {
                throw new IllegalCookieException();
            }
            return extractedEmail;
        } catch (IllegalTokenException e) {
            throw new IllegalCookieException();
        }
    }

    public String extractMemberRolePayload(String token) {
        try {
            String extractedMemberRole = jwtTokenProvider.extractMemberRole(token);
            if (extractedMemberRole == null) {
                throw new IllegalCookieException();
            }
            return extractedMemberRole;
        } catch (IllegalTokenException e) {
            throw new IllegalCookieException();
        }
    }
}
