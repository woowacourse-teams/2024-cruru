package com.cruru.email.exception.badrequest;

import com.cruru.advice.badrequest.TooManyRequestException;

public class EmailDuplicatedRequestException extends TooManyRequestException {

    private static final String MESSAGE = ": 발송 대기 중인 이메일 요청과 중복됩니다.";

    public EmailDuplicatedRequestException(String emailAddress) {
        super(emailAddress + MESSAGE);
    }
}
