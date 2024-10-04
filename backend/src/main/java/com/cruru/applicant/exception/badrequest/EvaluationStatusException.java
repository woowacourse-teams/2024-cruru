package com.cruru.applicant.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class EvaluationStatusException extends BadRequestException {

    private static final String MESSAGE = "지원하는 평가 상태가 아닙니다.";

    public EvaluationStatusException() {
        super(MESSAGE);
    }
}
