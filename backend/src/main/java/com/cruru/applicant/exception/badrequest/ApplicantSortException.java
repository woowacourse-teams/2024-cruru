package com.cruru.applicant.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class ApplicantSortException extends BadRequestException {

    private static final String MESSAGE = "지원하는 정렬 조건이 아닙니다.";

    public ApplicantSortException() {
        super(MESSAGE);
    }
}
