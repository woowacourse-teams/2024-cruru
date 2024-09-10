package com.cruru.advice;

public class UnauthorizedException extends CruruCustomException {

    private static final String STATUS_CODE = "401";

    public UnauthorizedException(String message) {
        super(message, STATUS_CODE);
    }
}
