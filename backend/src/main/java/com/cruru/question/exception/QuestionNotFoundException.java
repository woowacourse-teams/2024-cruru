package com.cruru.question.exception;

import com.cruru.advice.NotFoundException;

public class QuestionNotFoundException extends NotFoundException {

    private static final String TEXT = "존재하지 않는 질문입니다.";

    public QuestionNotFoundException() {
        super(TEXT);
    }
}
