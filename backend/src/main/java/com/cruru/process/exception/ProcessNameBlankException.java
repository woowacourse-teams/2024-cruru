package com.cruru.process.exception;

import com.cruru.advice.badrequest.TextBlankException;

public class ProcessNameBlankException extends TextBlankException {

    private static final String TEXT = "프로세스 이름";

    public ProcessNameBlankException() {
        super(TEXT);
    }
}
