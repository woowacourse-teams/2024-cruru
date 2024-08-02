package com.cruru.process.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class ProcessDeleteRemainingApplicantException extends BadRequestException {

    private static final String MESSAGE = "지원자가 존재하는 프로세스는 삭제할 수 없습니다.";

    public ProcessDeleteRemainingApplicantException() {
        super(MESSAGE);
    }
}
