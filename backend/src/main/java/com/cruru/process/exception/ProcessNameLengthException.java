package com.cruru.process.exception;

import com.cruru.advice.BadRequestException;

public class ProcessNameLengthException extends BadRequestException {

    private static final String MESSAGE = "프로세스 이름은 최대 %d자입니다. 현재 이름 글자수: %d";

    public ProcessNameLengthException(int maxLength, int currentLength) {
        super(String.format(MESSAGE, maxLength, currentLength));
    }
}
