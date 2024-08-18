package com.cruru.process.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class ProcessDeleteUnmodifiableException extends BadRequestException {

    private static final String MESSAGE = "삭제가 불가능한 프로세스입니다.";

    public ProcessDeleteUnmodifiableException() {
        super(MESSAGE);
    }
}
