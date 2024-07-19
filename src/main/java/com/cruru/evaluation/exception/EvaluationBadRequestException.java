package com.cruru.evaluation.exception;

import com.cruru.advice.BadRequestException;

public class EvaluationBadRequestException extends BadRequestException {

    public EvaluationBadRequestException(String message) {
        super(message);
    }
}
