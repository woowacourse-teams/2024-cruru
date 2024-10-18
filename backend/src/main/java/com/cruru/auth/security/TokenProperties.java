package com.cruru.auth.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("security.jwt.token")
public record TokenProperties(String secretKey, Long accessExpireLength, Long refreshExpireLength, String algorithm) {

}
