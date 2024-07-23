package com.cruru.process.exception;

import com.cruru.advice.badrequest.BadRequestException;

public class ProcessDeleteEndsException extends BadRequestException {

    private static final String MESSAGE = "처음과 마지막 프로세스는 삭제할 수 없습니다.";

    public ProcessDeleteEndsException() {
        super(MESSAGE);
    }
}
