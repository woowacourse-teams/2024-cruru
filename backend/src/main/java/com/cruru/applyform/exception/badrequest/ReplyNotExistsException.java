package com.cruru.applyform.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class ReplyNotExistsException extends BadRequestException {

    private static final String MESSAGE = "응답하지 않은 필수 질문이 존재합니다.";

    public ReplyNotExistsException() {
        super(MESSAGE);
    }
}
