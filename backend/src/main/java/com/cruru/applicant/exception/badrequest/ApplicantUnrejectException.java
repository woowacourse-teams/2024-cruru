package com.cruru.applicant.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class ApplicantUnrejectException extends BadRequestException {

    private static final String MESSAGE = "불합격하지 않은 지원자입니다.";

    public ApplicantUnrejectException() {
        super(MESSAGE);
    }
}
