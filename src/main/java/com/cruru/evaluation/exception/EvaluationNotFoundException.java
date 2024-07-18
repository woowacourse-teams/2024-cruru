package com.cruru.evaluation.exception;

import com.cruru.advice.NotFoundException;

public class EvaluationNotFoundException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 평가입니다.";

    public EvaluationNotFoundException() {
        super(MESSAGE);
    }
}
