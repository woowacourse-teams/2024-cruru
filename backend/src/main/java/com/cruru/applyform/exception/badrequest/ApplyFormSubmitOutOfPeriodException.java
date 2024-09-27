package com.cruru.applyform.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class ApplyFormSubmitOutOfPeriodException extends BadRequestException {

    private static final String MESSAGE = "접수 기간이 아닙니다.";

    public ApplyFormSubmitOutOfPeriodException() {
        super(MESSAGE);
    }
}
