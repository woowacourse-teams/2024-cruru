package com.cruru.advice.badrequest;

public class TextBlankException extends BadRequestException {

    private static final String MESSAGE = "%s이(가) 공백입니다.";

    public TextBlankException(String text) {
        super(String.format(MESSAGE, text));
    }
}
