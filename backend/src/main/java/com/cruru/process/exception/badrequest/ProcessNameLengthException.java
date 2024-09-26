package com.cruru.process.exception.badrequest;

import com.cruru.advice.badrequest.TextLengthException;

public class ProcessNameLengthException extends TextLengthException {

    private static final String TEXT = "프로세스 이름";

    public ProcessNameLengthException(int maxLength, int currentLength) {
        super(TEXT, maxLength, currentLength);
    }
}
