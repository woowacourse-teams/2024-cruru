package com.cruru.applicant.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class EvaluationScoreException extends BadRequestException {

    private static final String MESSAGE = "평가 점수는 %d~%d 범위 안에 있어야 합니다. 현재 점수: %d.";

    public EvaluationScoreException(int minScore, int maxScore, int currentScore) {
        super(String.format(MESSAGE, minScore, maxScore, currentScore));
    }
}
