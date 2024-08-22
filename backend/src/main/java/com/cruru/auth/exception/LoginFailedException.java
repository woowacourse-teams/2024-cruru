package com.cruru.auth.exception;

import com.cruru.advice.UnauthorizedException;

public class LoginFailedException extends UnauthorizedException {

    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";

    public LoginFailedException() {
        super(MESSAGE);
    }
}
