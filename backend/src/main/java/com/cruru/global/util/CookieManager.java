package com.cruru.global.util;

import com.cruru.auth.exception.IllegalCookieException;
import com.cruru.auth.security.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieManager {

    private final JwtTokenProvider tokenProvider;

    public static String extractToken(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> "token".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(IllegalCookieException::new);
    }

    public static Cookie[] extractCookie(HttpServletRequest request) {
        Cookie[] cookies = Objects.requireNonNull(request)
                .getCookies();

        if (cookies == null) {
            throw new IllegalCookieException();
        }
        return cookies;
    }
}
