package com.cruru.applyform.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class InvalidSubmitDateException extends BadRequestException {

    private static final String MESSAGE = "접수 기간이 아닙니다.";

    public InvalidSubmitDateException() {
        super(MESSAGE);
    }
}
