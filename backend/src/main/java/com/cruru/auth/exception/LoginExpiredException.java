package com.cruru.auth.exception;

import com.cruru.advice.UnauthorizedException;

public class LoginExpiredException extends UnauthorizedException {

    private static final String MESSAGE = "로그인이 만료되었습니다.";

    public LoginExpiredException() {
        super(MESSAGE);
    }
}
