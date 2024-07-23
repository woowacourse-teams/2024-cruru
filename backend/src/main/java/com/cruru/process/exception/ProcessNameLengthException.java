package com.cruru.process.exception;

import com.cruru.advice.BadRequestException;

public class ProcessNameLengthException extends BadRequestException {

    public static final String MESSAGE = "프로세스 이름이 비어있거나 %d자 초과입니다. 현재 이름 글자수: %d";

    public ProcessNameLengthException(int maxLength, int currentLength) {
        super(String.format(MESSAGE, maxLength, currentLength));
    }
}
