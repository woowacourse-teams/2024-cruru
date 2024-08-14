package com.cruru.advice;

public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(String message) {
        super(message);
    }
}
