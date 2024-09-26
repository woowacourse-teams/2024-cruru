package com.cruru.club.exception;

import com.cruru.advice.NotFoundException;

public class ClubNotFoundException extends NotFoundException {

    private static final String TARGET = "동아리";

    public ClubNotFoundException() {
        super(TARGET);
    }
}
