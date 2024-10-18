package com.cruru.auth.service;

import com.cruru.auth.controller.response.TokenResponse;
import com.cruru.auth.domain.AccessToken;
import com.cruru.auth.domain.RefreshToken;
import com.cruru.auth.domain.Token;
import com.cruru.auth.domain.repository.RefreshTokenRepository;
import com.cruru.auth.exception.IllegalTokenException;
import com.cruru.auth.exception.LoginExpiredException;
import com.cruru.auth.security.PasswordValidator;
import com.cruru.auth.security.TokenProperties;
import com.cruru.auth.security.TokenProvider;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
    private final TokenProperties tokenProperties;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    public Token createAccessToken(Member member) {
        Map<String, Object> claims = getClaims(member);
        String token = tokenProvider.createToken(claims, tokenProperties.accessExpireLength());
        return new AccessToken(token);
    }

    @Transactional
    public Token createRefreshToken(Member member) {
        if (refreshTokenRepository.existsByMember(member)) {
            return rotateRefreshToken(member);
        }

        Map<String, Object> claims = getClaims(member);
        String token = tokenProvider.createToken(claims, tokenProperties.refreshExpireLength());

        return refreshTokenRepository.save(new RefreshToken(token, member));
    }

    @Transactional
    public TokenResponse refresh(String refreshToken) {
        checkRefreshTokenExists(refreshToken);

        String email = extractEmail(refreshToken);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow();
        validMemberRefreshToken(refreshToken, member);
        return rotateTokens(member);
    }

    private void checkRefreshTokenExists(String refreshToken) {
        if (!isTokenSignatureValid(refreshToken)) {
            throw new IllegalTokenException();
        }

        if (!refreshTokenRepository.existsByToken(refreshToken)) {
            throw new IllegalTokenException();
        }

        if (isTokenExpired(refreshToken)) {
            throw new LoginExpiredException();
        }
    }

    private TokenResponse rotateTokens(Member member) {
        Token accessToken = createAccessToken(member);
        Token refreshToken = rotateRefreshToken(member);
        return new TokenResponse(accessToken.getToken(), refreshToken.getToken());
    }

    private Token rotateRefreshToken(Member member) {
        Map<String, Object> claims = getClaims(member);
        String token = tokenProvider.createToken(claims, tokenProperties.refreshExpireLength());
        RefreshToken refreshToken = new RefreshToken(token, member);

        refreshTokenRepository.updateRefreshTokenByMember(refreshToken.getToken(), member);
        return refreshToken;
    }

    private Map<String, Object> getClaims(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(EMAIL_CLAIM, member.getEmail());
        claims.put(ROLE_CLAIM, member.getRole().name());
        return claims;
    }

    public boolean isTokenExpired(String token) {
        return tokenProvider.isTokenExpired(token);
    }

    public boolean isTokenSignatureValid(String token) {
        try {
            return tokenProvider.isSignatureValid(token);
        } catch (IllegalTokenException e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return extractClaim(token, EMAIL_CLAIM);
    }

    public String extractMemberRole(String token) {
        return extractClaim(token, ROLE_CLAIM);
    }

    private String extractClaim(String token, String key) {
        String claim;
        try {
            claim = tokenProvider.extractClaim(token, key);
        } catch (ExpiredJwtException e) {
            Claims claims = e.getClaims();
            return claims.get(key, String.class);
        }
        if (claim == null) {
            throw new IllegalTokenException();
        }
        return claim;
    }

    public boolean isNotVerifiedPassword(String rawPassword, String encodedPassword) {
        return !passwordValidator.matches(rawPassword, encodedPassword);
    }

    private void validMemberRefreshToken(String refreshToken, Member member) {
        RefreshToken foundToken = refreshTokenRepository.findByMember(member)
                .orElseThrow(IllegalTokenException::new);
        if (!foundToken.isSameToken(refreshToken)) {
            throw new IllegalTokenException();
        }
    }
}
