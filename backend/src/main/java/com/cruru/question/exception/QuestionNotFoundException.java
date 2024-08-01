package com.cruru.question.exception;

import com.cruru.advice.NotFoundException;

public class QuestionNotFoundException extends NotFoundException {

    private static final String TEXT = "질문";

    public QuestionNotFoundException() {
        super(TEXT);
    }
}
