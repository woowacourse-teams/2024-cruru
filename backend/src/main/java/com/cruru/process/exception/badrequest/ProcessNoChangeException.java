package com.cruru.process.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class ProcessNoChangeException extends BadRequestException {

    private static final String MESSAGE = "변경된 정보가 없습니다.";

    public ProcessNoChangeException() {
        super(MESSAGE);
    }
}
