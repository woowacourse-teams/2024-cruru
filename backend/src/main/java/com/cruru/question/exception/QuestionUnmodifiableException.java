package com.cruru.question.exception;

import com.cruru.advice.badrequest.BadRequestException;

public class QuestionUnmodifiableException extends BadRequestException {

    private static final String MESSAGE = "진행중인 모집 공고는 질문을 수정할 수 없습니다.";

    public QuestionUnmodifiableException() {
        super(MESSAGE);
    }

}
