package com.cruru.auth.domain;

public class AccessToken implements Token {

    private final String token;

    public AccessToken(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "token='" + token + '\'' +
                '}';
    }
}
