package com.cruru.email.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class VerificationCodeNotFoundException extends BadRequestException {

    private static final String MESSAGE = "인증 코드가 존재하지 않거나 만료되었습니다.";

    public VerificationCodeNotFoundException() {
        super(MESSAGE);
    }
}
