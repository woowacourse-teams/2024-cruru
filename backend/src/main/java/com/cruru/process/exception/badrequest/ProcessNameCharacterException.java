package com.cruru.process.exception.badrequest;

import com.cruru.advice.badrequest.TextCharacterException;

public class ProcessNameCharacterException extends TextCharacterException {

    private static final String TEXT = "프로세스 이름";

    public ProcessNameCharacterException(String invalidCharacters) {
        super(TEXT, invalidCharacters);
    }
}
