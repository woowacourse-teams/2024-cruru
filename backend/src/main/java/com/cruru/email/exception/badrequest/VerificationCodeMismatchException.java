package com.cruru.email.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class VerificationCodeMismatchException extends BadRequestException {

    private static final String MESSAGE = "인증 코드가 일치하지 않습니다.";

    public VerificationCodeMismatchException() {
        super(MESSAGE);
    }
}
