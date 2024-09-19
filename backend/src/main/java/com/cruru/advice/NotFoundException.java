package com.cruru.advice;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CruruCustomException {

    private static final String MESSAGE = "존재하지 않는 %s입니다.";
    private static final String STATUS_CODE = HttpStatus.NOT_FOUND.toString();

    public NotFoundException(String target) {
        super(String.format(MESSAGE, target), STATUS_CODE);
    }
}
