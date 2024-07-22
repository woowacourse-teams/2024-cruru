package com.cruru.advice;

public class NonAuthorizedException extends RuntimeException {

    public NonAuthorizedException(String message) {
        super(message);
    }
}
