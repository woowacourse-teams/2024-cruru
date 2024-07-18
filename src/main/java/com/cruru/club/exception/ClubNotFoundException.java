package com.cruru.club.exception;

import com.cruru.advice.NotFoundException;

public class ClubNotFoundException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 동아리입니다.";

    public ClubNotFoundException() {
        super(MESSAGE);
    }
}
