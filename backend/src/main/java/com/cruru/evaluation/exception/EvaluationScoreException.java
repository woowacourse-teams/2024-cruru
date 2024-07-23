package com.cruru.evaluation.exception;

import com.cruru.advice.BadRequestException;

public class EvaluationScoreException extends BadRequestException {

    private static final String MESSAGE = "평가 점수가 %d 미만이거나 %d 초과입니다. 현재 점수: %d.";

    public EvaluationScoreException(int minScore, int maxScore, int currentScore) {
        super(String.format(MESSAGE, minScore, maxScore, currentScore));
    }
}
