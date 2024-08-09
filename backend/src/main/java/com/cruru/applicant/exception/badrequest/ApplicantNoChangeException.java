package com.cruru.applicant.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class ApplicantNoChangeException extends BadRequestException {

    private static final String MESSAGE = "변경된 내용이 없습니다.";

    public ApplicantNoChangeException() {
        super(MESSAGE);
    }
}
