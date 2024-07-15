package com.cruru.process.exception;

import com.cruru.advice.NotFoundException;

public class ProcessNotFoundException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 프로세스입니다.";

    public ProcessNotFoundException() {
        super(MESSAGE);
    }
}
