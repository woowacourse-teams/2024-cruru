package com.cruru.auth.security;

import com.cruru.auth.exception.IllegalTokenException;
import com.cruru.member.domain.Member;

public interface TokenProvider {

    String createToken(Member member);

    boolean isExpired(String token) throws IllegalTokenException;

    String extractPayload(String token, String key) throws IllegalTokenException;
}
