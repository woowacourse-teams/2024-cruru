package com.cruru.advice;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CruruCustomException {

    private static final String STATUS_CODE = HttpStatus.UNAUTHORIZED.toString();

    public UnauthorizedException(String message) {
        super(message, STATUS_CODE);
    }
}
