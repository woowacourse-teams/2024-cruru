package com.cruru.auth.exception;

import com.cruru.advice.badrequest.BadRequestException;

public class IllegalCookieException extends BadRequestException {

    private static final String MESSAGE = "유효하지 않은 쿠키입니다.";

    public IllegalCookieException() {
        super(MESSAGE);
    }
}
