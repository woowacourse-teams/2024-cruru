package com.cruru.global.util;

import com.cruru.auth.exception.IllegalCookieException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CookieManager {

    private static CookieProperties cookieProperties;

    @Autowired
    public void setCookieProperties(CookieProperties cookieProperties) {
        CookieManager.cookieProperties = cookieProperties;
    }

    public static String extractToken(HttpServletRequest request) {
        Cookie[] cookies = extractCookie(request);
        return Arrays.stream(cookies)
                .filter(cookie -> cookieProperties.accessTokenKey().equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(IllegalCookieException::new);
    }

    private static Cookie[] extractCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            throw new IllegalCookieException();
        }
        return cookies;
    }

    public static ResponseCookie createTokenCookie(String token) {
        return ResponseCookie.from(cookieProperties.accessTokenKey(), token)
                .httpOnly(cookieProperties.httpOnly())
                .secure(cookieProperties.secure())
                .domain(cookieProperties.domain())
                .path(cookieProperties.path())
                .sameSite(cookieProperties.sameSite())
                .maxAge(cookieProperties.maxAge())
                .build();
    }
}
