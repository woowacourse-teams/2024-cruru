package com.cruru.advice;

public class NotFoundException extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 %s입니다.";

    public NotFoundException(String target) {
        super(String.format(MESSAGE, target));
    }
}
