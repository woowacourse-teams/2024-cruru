package com.cruru.process.exception;

import com.cruru.advice.duplicated.TextLengthException;

public class ProcessNameLengthException extends TextLengthException {

    private static final String TEXT = "프로세스 이름";

    public ProcessNameLengthException(int maxLength, int currentLength) {
        super(TEXT, maxLength, currentLength);
    }
}
