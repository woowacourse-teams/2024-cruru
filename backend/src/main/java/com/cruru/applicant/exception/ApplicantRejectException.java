package com.cruru.applicant.exception;

import com.cruru.advice.badrequest.BadRequestException;

public class ApplicantRejectException extends BadRequestException {

    private static final String MESSAGE = "이미 불합격한 지원자입니다.";

    public ApplicantRejectException() {
        super(MESSAGE);
    }
}
