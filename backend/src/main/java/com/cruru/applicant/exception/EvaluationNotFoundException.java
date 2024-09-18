package com.cruru.applicant.exception;

import com.cruru.advice.NotFoundException;

public class EvaluationNotFoundException extends NotFoundException {

    private static final String TARGET = "평가";

    public EvaluationNotFoundException() {
        super(TARGET);
    }
}
