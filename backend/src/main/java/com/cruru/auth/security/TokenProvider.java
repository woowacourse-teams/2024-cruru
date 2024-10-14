package com.cruru.auth.security;

import com.cruru.auth.exception.IllegalTokenException;
import java.util.Map;

public interface TokenProvider {

    String createToken(Map<String, Object> claims, Long expireLength);

    boolean isAlive(String token) throws IllegalTokenException;

    String extractClaim(String token, String key) throws IllegalTokenException;
}
