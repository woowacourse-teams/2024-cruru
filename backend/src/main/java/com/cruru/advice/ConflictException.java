package com.cruru.advice;

import org.springframework.http.HttpStatus;

public class ConflictException extends CruruCustomException {

    private static final String STATUS_CODE = HttpStatus.CONFLICT.toString();

    public ConflictException(String message) {
        super(message, STATUS_CODE);
    }
}
