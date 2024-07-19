package com.cruru.club.exception;

import com.cruru.advice.BadRequestException;

public class ClubBadRequestException extends BadRequestException {

    private static final String MESSAGE = "잘못된 동아리 관련 요청입니다.";

    public ClubBadRequestException() {
        super(MESSAGE);
    }
}
