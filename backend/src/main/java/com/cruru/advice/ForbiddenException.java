package com.cruru.advice;

public class ForbiddenException extends RuntimeException {

    private static final String MESSAGE = "접근 권한이 없습니다";

    public ForbiddenException() {
        super(MESSAGE);
    }
}
