package com.cruru.process.exception;

import com.cruru.advice.BadRequestException;

public class ProcessNameBlankException extends BadRequestException {

    private static final String MESSAGE = "프로세스 이름이 공백입니다.";

    public ProcessNameBlankException() {
        super(MESSAGE);
    }
}
