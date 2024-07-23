package com.cruru.advice.duplicated;

import com.cruru.advice.BadRequestException;

public class TextLengthException extends BadRequestException {

    private static final String MESSAGE = "%s은(는) 최대 %d자입니다. 현재 글자수: %d";

    public TextLengthException(String text, int maxLength, int currentLength) {
        super(String.format(MESSAGE, text, currentLength, maxLength));
    }
}
