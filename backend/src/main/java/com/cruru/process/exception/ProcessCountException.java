package com.cruru.process.exception;

import com.cruru.advice.badrequest.BadRequestException;

public class ProcessCountException extends BadRequestException {

    private static final String MESSAGE = "프로세스는 최대 %d개까지 생성 가능합니다";

    public ProcessCountException(int maxCount) {
        super(String.format(MESSAGE, maxCount));
    }
}
