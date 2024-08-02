package com.cruru.choice.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class ChoiceIllegalSaveException extends BadRequestException {

    private static final String MESSAGE = "선택지를 저장할 수 없는 질문입니다.";

    public ChoiceIllegalSaveException() {
        super(MESSAGE);
    }
}
