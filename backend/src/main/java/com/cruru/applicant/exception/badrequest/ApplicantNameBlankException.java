package com.cruru.applicant.exception.badrequest;

import com.cruru.advice.badrequest.TextBlankException;

public class ApplicantNameBlankException extends TextBlankException {

    private static final String TEXT = "지원자 이름";

    public ApplicantNameBlankException() {
        super(TEXT);
    }
}
