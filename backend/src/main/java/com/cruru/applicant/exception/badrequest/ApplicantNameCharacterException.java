package com.cruru.applicant.exception.badrequest;

import com.cruru.advice.badrequest.TextCharacterException;

public class ApplicantNameCharacterException extends TextCharacterException {

    private static final String TEXT = "지원자 이름";

    public ApplicantNameCharacterException(String invalidText) {
        super(TEXT, invalidText);
    }
}
