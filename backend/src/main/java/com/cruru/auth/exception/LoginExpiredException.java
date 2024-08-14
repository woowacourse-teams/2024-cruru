package com.cruru.auth.exception;

import com.cruru.advice.UnAuthorizedException;

public class LoginExpiredException extends UnAuthorizedException {

    private static final String MESSAGE = "로그인이 만료되었습니다.";

    public LoginExpiredException() {
        super(MESSAGE);
    }
}
