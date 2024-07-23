package com.cruru.process.exception;

import com.cruru.advice.duplicated.TextCharacterException;

public class ProcessNameCharacterException extends TextCharacterException {

    private static final String TEXT = "프로세스 이름";

    public ProcessNameCharacterException(String invalidCharacters) {
        super(TEXT, invalidCharacters);
    }
}
