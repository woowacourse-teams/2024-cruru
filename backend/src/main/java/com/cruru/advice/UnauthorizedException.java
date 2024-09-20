package com.cruru.advice;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CruruCustomException {

    private static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;

    public UnauthorizedException(String message) {
        super(message, STATUS);
    }
}
