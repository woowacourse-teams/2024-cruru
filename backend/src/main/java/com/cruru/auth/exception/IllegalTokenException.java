package com.cruru.auth.exception;

public class IllegalTokenException extends RuntimeException {

    private static final String MESSAGE = "유효하지 않는 토큰입니다.";

    public IllegalTokenException() {
        super(MESSAGE);
    }
}
