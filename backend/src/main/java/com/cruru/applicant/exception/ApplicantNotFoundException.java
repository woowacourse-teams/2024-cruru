package com.cruru.applicant.exception;

import com.cruru.advice.NotFoundException;

public class ApplicantNotFoundException extends NotFoundException {

    private static final String TARGET = "지원자";

    public ApplicantNotFoundException() {
        super(TARGET);
    }
}
