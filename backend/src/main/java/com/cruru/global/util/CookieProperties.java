package com.cruru.global.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cookie")
public record CookieProperties(
        String accessTokenKey,
        String refreshTokenKey,
        boolean httpOnly,
        boolean secure,
        String domain,
        String path,
        String sameSite,
        Long maxAge
) {

}
