package com.cruru.email.exception;

import com.cruru.advice.InternalServerException;

public class EmailSendFailedException extends InternalServerException {

    private static final String TEXT = "이메일 전송에 실패했습니다.";

    public EmailSendFailedException() {
        super(TEXT);
    }
}
