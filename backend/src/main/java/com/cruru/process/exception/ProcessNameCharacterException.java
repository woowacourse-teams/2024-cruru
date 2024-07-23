package com.cruru.process.exception;

import com.cruru.advice.BadRequestException;

public class ProcessNameCharacterException extends BadRequestException {

    private static final String MESSAGE = "프로세스 이름에 \\ 혹은 |를 포함하고 있습니다. 입력된 이름: %s";

    public ProcessNameCharacterException(String invalidName) {
        super(String.format(MESSAGE, invalidName));
    }
}
