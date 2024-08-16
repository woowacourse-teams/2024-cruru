package com.cruru.auth.security;

import com.cruru.auth.exception.IllegalTokenException;
import java.util.Map;

public interface TokenProvider {

    String createToken(Map<String, Object> claims);

    boolean isExpired(String token) throws IllegalTokenException;

    String extractPayload(String token, String key) throws IllegalTokenException;
}
