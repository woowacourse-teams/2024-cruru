package com.cruru.process.exception;

import com.cruru.advice.BadRequestException;

public class ProcessCountException extends BadRequestException {

    private static final String MESSAGE = "프로세스 수가 최대 %d개이므로 더 이상 생성할 수 없습니다.";

    public ProcessCountException(int maxCount) {
        super(String.format(MESSAGE, maxCount));
    }
}
