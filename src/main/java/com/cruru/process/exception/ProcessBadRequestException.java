package com.cruru.process.exception;

import com.cruru.advice.BadRequestException;

public class ProcessBadRequestException extends BadRequestException {

    private static final String MESSAGE = "잘못된 프로세스 관련 요청입니다.";

    public ProcessBadRequestException() {
        super(MESSAGE);
    }
}
