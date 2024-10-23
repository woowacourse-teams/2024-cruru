package com.cruru.email.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class EmailAlreadySignedUpException extends BadRequestException {

    private static final String MESSAGE = "이미 가입된 이메일입니다.";

    public EmailAlreadySignedUpException() {
        super(MESSAGE);
    }
}
