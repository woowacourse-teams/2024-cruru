package com.cruru.club.exception;

import com.cruru.advice.BadRequestException;

public class ClubNameBlankException extends BadRequestException {

    private static final String MESSAGE = "동아리 이름이 공백입니다.";

    public ClubNameBlankException() {
        super(MESSAGE);
    }
}
