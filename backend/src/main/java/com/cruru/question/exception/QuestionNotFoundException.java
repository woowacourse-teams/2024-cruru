package com.cruru.question.exception;

import com.cruru.advice.NotFoundException;

public class QuestionNotFoundException extends NotFoundException {

    private static final String MESSAGE = "질문이 존재하지 않습니다.";

    public QuestionNotFoundException() {
        super(MESSAGE);
    }
}
