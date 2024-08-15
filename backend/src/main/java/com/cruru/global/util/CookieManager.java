package com.cruru.global.util;

import com.cruru.auth.exception.IllegalCookieException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CookieManager {

    public static String extractToken(HttpServletRequest request) {
        Cookie[] cookies = extractCookie(request);
        return Arrays.stream(cookies)
                .filter(cookie -> "token".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(IllegalCookieException::new);
    }

    private static Cookie[] extractCookie(HttpServletRequest request) {
        Cookie[] cookies = Objects.requireNonNull(request)
                .getCookies();

        if (cookies == null) {
            throw new IllegalCookieException();
        }
        return cookies;
    }
}
