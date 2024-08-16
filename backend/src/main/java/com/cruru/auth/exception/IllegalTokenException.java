package com.cruru.auth.exception;

import com.cruru.advice.UnauthorizedException;

public class IllegalTokenException extends UnauthorizedException {

    private static final String MESSAGE = "유효하지 않는 토큰입니다.";

    public IllegalTokenException() {
        super(MESSAGE);
    }
}
