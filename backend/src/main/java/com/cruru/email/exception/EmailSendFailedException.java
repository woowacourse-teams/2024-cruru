package com.cruru.email.exception;

import com.cruru.advice.InternalServerException;

public class EmailSendFailedException extends InternalServerException {

    private static final String MESSAGE = "이메일 전송에 실패했습니다. 발송자 Id: %d, 수신자 Email: %s";

    public EmailSendFailedException(Long from, String to) {
        super(String.format(MESSAGE, from, to));
    }
}
