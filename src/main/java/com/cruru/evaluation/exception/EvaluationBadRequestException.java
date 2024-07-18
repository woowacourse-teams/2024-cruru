package com.cruru.evaluation.exception;

import com.cruru.advice.BadRequestException;

public class EvaluationBadRequestException extends BadRequestException {

    private static final String MESSAGE = "잘못된 평가 관련 요청입니다.";

    public EvaluationBadRequestException() {
        super(MESSAGE);
    }
}
