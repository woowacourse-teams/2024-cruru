package com.cruru.advice;

public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
