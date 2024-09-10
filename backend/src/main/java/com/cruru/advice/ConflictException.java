package com.cruru.advice;

public class ConflictException extends CruruCustomException {

    private static final String STATUS_CODE = "409";

    public ConflictException(String message) {
        super(message, STATUS_CODE);
    }
}
