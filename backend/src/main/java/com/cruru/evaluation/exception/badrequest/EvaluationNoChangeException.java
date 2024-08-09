package com.cruru.evaluation.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class EvaluationNoChangeException extends BadRequestException {

    private static final String MESSAGE = "변경된 내용이 없습니다.";

    public EvaluationNoChangeException() {
        super(MESSAGE);
    }
}
