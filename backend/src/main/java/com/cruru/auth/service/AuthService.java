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
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
        log.info("Creating access token for email: {}, role: {}", email, role);
        Map<String, Object> claims = getClaims(email, role);
        String token = tokenProvider.createToken(claims, tokenProperties.accessExpireLength());
        log.debug("Access token created: {}", token);
        return new AccessToken(token);
    }

    @Transactional
    public Token createRefreshToken(String email, MemberRole role) {
        log.info("Creating refresh token for email: {}", email);
        if (tokenRedisClient.existsByEmail(email)) {
            log.debug("Existing refresh token found, rotating token");
            return rotateRefreshToken(email, role);
        }

        Map<String, Object> claims = getClaims(email, role);
        String token = tokenProvider.createToken(claims, tokenProperties.refreshExpireLength());
        tokenRedisClient.saveToken(email, token);
        log.debug("New refresh token created and saved to Redis: {}", token);

        return new RefreshToken(token, email);
    }

    @Transactional
    public TokenResponse refresh(String refreshToken) {
        log.info("Refreshing tokens using refresh token");
        String email = extractEmail(refreshToken);
        checkRefreshTokenExists(email, refreshToken);
        MemberRole role = MemberRole.valueOf(extractMemberRole(refreshToken));
        validMemberRefreshToken(refreshToken, email);
        log.debug("Refresh token valid, rotating tokens");
        return rotateTokens(email, role);
    }

    private void checkRefreshTokenExists(String email, String refreshToken) {
        log.info("Checking if refresh token exists for email: {}", email);
        if (!isTokenSignatureValid(refreshToken)) {
            log.error("Refresh token signature is invalid for token: {}", refreshToken);
            throw new IllegalTokenException();
        }

        if (!tokenRedisClient.existsByToken(email, refreshToken)) {
            log.error("Refresh token does not exist in Redis for email: {}", email);
            throw new IllegalTokenException();
        }

        if (isTokenExpired(refreshToken)) {
            log.warn("Refresh token expired for email: {}", email);
            throw new LoginExpiredException();
        }
    }

    private TokenResponse rotateTokens(String email, MemberRole role) {
        log.info("Rotating access and refresh tokens for email: {}", email);
        Token accessToken = createAccessToken(email, role);
        Token refreshToken = rotateRefreshToken(email, role);
        log.debug("Tokens rotated successfully for email: {}", email);
        return new TokenResponse(accessToken.getToken(), refreshToken.getToken());
    }

    private Token rotateRefreshToken(String email, MemberRole role) {
        log.info("Rotating refresh token for email: {}", email);
        Map<String, Object> claims = getClaims(email, role);
        String token = tokenProvider.createToken(claims, tokenProperties.refreshExpireLength());
        RefreshToken refreshToken = new RefreshToken(token, email);
        tokenRedisClient.saveToken(email, refreshToken.getToken());
        log.debug("New refresh token saved to Redis: {}", refreshToken.getToken());
        return refreshToken;
    }

    private Map<String, Object> getClaims(String email, MemberRole role) {
        log.debug("Creating claims for email: {}, role: {}", email, role);
        return Map.of(
                EMAIL_CLAIM, email,
                ROLE_CLAIM, role.name()
        );
    }

    public boolean isTokenExpired(String token) {
        log.debug("Checking if token is expired: {}", token);
        return tokenProvider.isTokenExpired(token);
    }

    public boolean isTokenSignatureValid(String token) {
        log.debug("Checking if token signature is valid: {}", token);
        try {
            return tokenProvider.isSignatureValid(token);
        } catch (IllegalTokenException e) {
            log.error("Token signature is invalid: {}", token, e);
            return false;
        }
    }

    public String extractEmail(String token) {
        log.debug("Extracting email from token");
        return extractClaim(token, EMAIL_CLAIM);
    }

    public String extractMemberRole(String token) {
        log.debug("Extracting member role from token");
        return extractClaim(token, ROLE_CLAIM);
    }

    private String extractClaim(String token, String key) {
        log.debug("Extracting claim: {} from token", key);
        String claim;
        try {
            claim = tokenProvider.extractClaim(token, key);
        } catch (ExpiredJwtException e) {
            Claims claims = e.getClaims();
            log.warn("Token expired, extracting claim from expired token");
            return claims.get(key, String.class);
        }
        if (claim == null) {
            log.error("Claim {} not found in token", key);
            throw new IllegalTokenException();
        }
        return claim;
    }

    public boolean isNotVerifiedPassword(String rawPassword, String encodedPassword) {
        log.debug("Validating password");
        return !passwordValidator.matches(rawPassword, encodedPassword);
    }

    private void validMemberRefreshToken(String refreshToken, String email) {
        log.debug("Validating refresh token for email: {}", email);
        String foundToken = tokenRedisClient.getToken(email)
                .orElseThrow(() -> {
                    log.error("Refresh token not found in Redis for email: {}", email);
                    return new IllegalTokenException();
                });
        if (!foundToken.equals(refreshToken)) {
            log.error("Refresh token does not match the one in Redis for email: {}", email);
            throw new IllegalTokenException();
        }
    }
}
