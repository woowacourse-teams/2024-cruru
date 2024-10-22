package com.cruru.auth.service;

import com.cruru.auth.controller.response.TokenResponse;
import com.cruru.auth.domain.AccessToken;
import com.cruru.auth.domain.RefreshToken;
import com.cruru.auth.domain.Token;
import com.cruru.auth.exception.IllegalTokenException;
import com.cruru.auth.exception.LoginExpiredException;
import com.cruru.auth.security.PasswordValidator;
import com.cruru.auth.security.TokenProperties;
import com.cruru.auth.security.TokenProvider;
import com.cruru.member.domain.MemberRole;
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
    private final TokenRedisClient tokenRedisClient;

    public Token createAccessToken(String email, MemberRole role) {
        Map<String, Object> claims = getClaims(email, role);
        String token = tokenProvider.createToken(claims, tokenProperties.accessExpireLength());
        return new AccessToken(token);
    }

    @Transactional
    public Token createRefreshToken(String email, MemberRole role) {
        if (tokenRedisClient.existsByEmail(email)) {
            return rotateRefreshToken(email, role);
        }

        Map<String, Object> claims = getClaims(email, role);
        String token = tokenProvider.createToken(claims, tokenProperties.refreshExpireLength());
        tokenRedisClient.saveToken(email, token);

        return new RefreshToken(token, email);
    }

    @Transactional
    public TokenResponse refresh(String refreshToken) {
        String email = extractEmail(refreshToken);
        checkRefreshTokenExists(email, refreshToken);
        MemberRole role = MemberRole.valueOf(extractMemberRole(refreshToken));
        validMemberRefreshToken(refreshToken, email);
        return rotateTokens(email, role);
    }

    private void checkRefreshTokenExists(String email, String refreshToken) {
        if (!isTokenSignatureValid(refreshToken)) {
            throw new IllegalTokenException();
        }

        if (!tokenRedisClient.existsByToken(email, refreshToken)) {
            throw new IllegalTokenException();
        }

        if (isTokenExpired(refreshToken)) {
            throw new LoginExpiredException();
        }
    }

    private TokenResponse rotateTokens(String email, MemberRole role) {
        Token accessToken = createAccessToken(email, role);
        Token refreshToken = rotateRefreshToken(email, role);
        return new TokenResponse(accessToken.getToken(), refreshToken.getToken());
    }

    private Token rotateRefreshToken(String email, MemberRole role) {
        Map<String, Object> claims = getClaims(email, role);
        String token = tokenProvider.createToken(claims, tokenProperties.refreshExpireLength());
        RefreshToken refreshToken = new RefreshToken(token, email);

        tokenRedisClient.saveToken(email, refreshToken.getToken());
        return refreshToken;
    }

    private Map<String, Object> getClaims(String email, MemberRole role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(EMAIL_CLAIM, email);
        claims.put(ROLE_CLAIM, role.name());
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

    private void validMemberRefreshToken(String refreshToken, String email) {
        String foundToken = tokenRedisClient.getToken(email)
                .orElseThrow(IllegalTokenException::new);
        if (!foundToken.equals(refreshToken)) {
            throw new IllegalTokenException();
        }
    }
}
