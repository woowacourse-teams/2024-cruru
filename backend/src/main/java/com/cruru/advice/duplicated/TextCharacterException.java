package com.cruru.advice.duplicated;

import com.cruru.advice.BadRequestException;

public class TextCharacterException extends BadRequestException {

    private static final String MESSAGE = "%s에 포함될 수 없는 글자가 존재합니다: %s.";

    public TextCharacterException(String text, String invalidText) {
        super(String.format(MESSAGE, text, invalidText));
    }
}
