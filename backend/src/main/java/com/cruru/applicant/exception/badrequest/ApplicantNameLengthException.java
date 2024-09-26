package com.cruru.applicant.exception.badrequest;

import com.cruru.advice.badrequest.TextLengthException;

public class ApplicantNameLengthException extends TextLengthException {

    private static final String TEXT = "지원자 이름";

    public ApplicantNameLengthException(int maxLength, int currentLength) {
        super(TEXT, maxLength, currentLength);
    }
}
