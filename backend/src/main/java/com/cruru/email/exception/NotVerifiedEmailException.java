package com.cruru.email.exception;

import com.cruru.advice.UnauthorizedException;

public class NotVerifiedEmailException extends UnauthorizedException {

    private static final String MESSAGE = "이메일 인증이 필요합니다.";

    public NotVerifiedEmailException() {
        super(MESSAGE);
    }
}

