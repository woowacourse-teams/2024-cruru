package com.cruru.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccessToken implements Token {

    private final String token;

    @Override
    public String toString() {
        return "AccessToken{" +
                "token='" + token + '\'' +
                '}';
    }
}
