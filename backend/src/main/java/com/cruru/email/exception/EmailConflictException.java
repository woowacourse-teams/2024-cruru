package com.cruru.email.exception;

import com.cruru.advice.ConflictException;

public class EmailConflictException extends ConflictException {

    private static final String MESSAGE = "이미 가입된 이메일입니다.";

    public EmailConflictException() {
        super(MESSAGE);
    }
}
