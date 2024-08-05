package com.cruru.choice.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class ChoiceEmptyException extends BadRequestException {

    private static final String MESSAGE = "객관식 질문에는 1개 이상의 객관식 선택지를 포함해야합니다.";

    public ChoiceEmptyException() {
        super(MESSAGE);
    }
}
