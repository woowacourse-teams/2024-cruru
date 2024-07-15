package com.cruru.advice;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super(message);
    }
}
