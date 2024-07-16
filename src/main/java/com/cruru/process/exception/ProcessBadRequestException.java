package com.cruru.process.exception;

import com.cruru.advice.BadRequestException;

public class ProcessBadRequestException extends BadRequestException {

    private static final String MESSAGE = "요청 데이터가 올바른 형식이 아닙니다.";

    public ProcessBadRequestException() {
        super(MESSAGE);
    }
}
