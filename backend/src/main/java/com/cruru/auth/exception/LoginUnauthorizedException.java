package com.cruru.auth.exception;

import com.cruru.advice.UnauthorizedException;

public class LoginUnauthorizedException extends UnauthorizedException {

    private static final String MESSAGE = "로그인 정보가 유효하지 않습니다.";

    public LoginUnauthorizedException() {
        super(MESSAGE);
    }
}
