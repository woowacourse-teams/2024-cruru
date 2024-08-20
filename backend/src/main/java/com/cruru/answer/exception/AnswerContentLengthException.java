package com.cruru.answer.exception;

import com.cruru.advice.badrequest.TextLengthException;

public class AnswerContentLengthException extends TextLengthException {

    private static final String TEXT = "답변";

    public AnswerContentLengthException(int maxLength, int currentLength) {
        super(TEXT, maxLength, currentLength);
    }
}
