package com.cruru.applicant.exception;

import com.cruru.advice.NotFoundException;

public class ApplicantNotFoundException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 지원자입니다.";

    public ApplicantNotFoundException() {
        super(MESSAGE);
    }
}
