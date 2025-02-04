package com.cruru.question.exception;

import com.cruru.advice.badrequest.TextLengthException;

public class QuestionDescriptionLengthException extends TextLengthException {

    private static final String TEXT = "질문의 설명";

    public QuestionDescriptionLengthException(int maxLength, int currentLength) {
        super(TEXT, maxLength, currentLength);
    }
}
