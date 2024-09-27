package com.cruru.advice;

import org.springframework.http.HttpStatus;

public class ConflictException extends CruruCustomException {

    private static final HttpStatus STATUS = HttpStatus.CONFLICT;

    public ConflictException(String message) {
        super(message, STATUS);
    }
}
