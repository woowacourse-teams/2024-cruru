package com.cruru.global.util;

import com.cruru.auth.exception.IllegalCookieException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CookieManager {

    private final CookieProperties cookieProperties;

    public String extractAccessToken(HttpServletRequest request) {
        log.info("Extracting access token from cookies");
        Cookie[] cookies = extractCookie(request);
        return Arrays.stream(cookies)
                .filter(this::isAccessTokenCookie)
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> {
                    log.error("Access token cookie not found or invalid");
                    return new IllegalCookieException();
                });
    }

    private Cookie[] extractCookie(HttpServletRequest request) {
        log.debug("Extracting cookies from request");
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            log.warn("No cookies found in the request");
            throw new IllegalCookieException();
        }
        return cookies;
    }

    private boolean isAccessTokenCookie(Cookie cookie) {
        boolean result = cookieProperties.accessTokenKey().equals(cookie.getName());
        log.debug("Is access token cookie: {}", result);
        return result;
    }

    public String extractRefreshToken(HttpServletRequest request) {
        log.info("Extracting refresh token from cookies");
        Cookie[] cookies = extractCookie(request);
        return Arrays.stream(cookies)
                .filter(this::isRefreshTokenCookie)
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> {
                    log.error("Refresh token cookie not found or invalid");
                    return new IllegalCookieException();
                });
    }

    private boolean isRefreshTokenCookie(Cookie cookie) {
        boolean result = cookieProperties.refreshTokenKey().equals(cookie.getName());
        log.debug("Is refresh token cookie: {}", result);
        return result;
    }

    public ResponseCookie createAccessTokenCookie(String token) {
        log.info("Creating access token cookie");
        ResponseCookie cookie = ResponseCookie.from(cookieProperties.accessTokenKey(), token)
                .httpOnly(cookieProperties.httpOnly())
                .secure(cookieProperties.secure())
                .domain(cookieProperties.domain())
                .path(cookieProperties.path())
                .sameSite(cookieProperties.sameSite())
                .maxAge(cookieProperties.maxAge())
                .build();
        log.debug("Access token cookie created: {}", cookie);
        return cookie;
    }

    public ResponseCookie createRefreshTokenCookie(String refreshToken) {
        log.info("Creating refresh token cookie");
        ResponseCookie cookie = ResponseCookie.from(cookieProperties.refreshTokenKey(), refreshToken)
                .httpOnly(cookieProperties.httpOnly())
                .secure(cookieProperties.secure())
                .domain(cookieProperties.domain())
                .path(cookieProperties.path())
                .sameSite(cookieProperties.sameSite())
                .maxAge(cookieProperties.maxAge())
                .build();
        log.debug("Refresh token cookie created: {}", cookie);
        return cookie;
    }

    public ResponseCookie clearAccessTokenCookie() {
        log.info("Clearing access token cookie");
        ResponseCookie cookie = ResponseCookie.from(cookieProperties.accessTokenKey())
                .httpOnly(cookieProperties.httpOnly())
                .secure(cookieProperties.secure())
                .domain(cookieProperties.domain())
                .path(cookieProperties.path())
                .sameSite(cookieProperties.sameSite())
                .maxAge(0)
                .build();
        log.debug("Access token cookie cleared: {}", cookie);
        return cookie;
    }

    public ResponseCookie clearRefreshTokenCookie() {
        log.info("Clearing refresh token cookie");
        ResponseCookie cookie = ResponseCookie.from(cookieProperties.refreshTokenKey())
                .httpOnly(cookieProperties.httpOnly())
                .secure(cookieProperties.secure())
                .domain(cookieProperties.domain())
                .path(cookieProperties.path())
                .sameSite(cookieProperties.sameSite())
                .maxAge(0)
                .build();
        log.debug("Refresh token cookie cleared: {}", cookie);
        return cookie;
    }
}
