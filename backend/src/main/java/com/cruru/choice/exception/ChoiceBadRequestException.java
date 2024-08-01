package com.cruru.choice.exception;

import com.cruru.advice.badrequest.BadRequestException;

public class ChoiceBadRequestException extends BadRequestException {

    private static final String MESSAGE = "선택지를 저장할 수 없는 질문입니다.";

    public ChoiceBadRequestException() {
        super(MESSAGE);
    }
}
