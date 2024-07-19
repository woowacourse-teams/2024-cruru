package com.cruru.club.exception;

import com.cruru.advice.BadRequestException;

public class ClubBadRequestException extends BadRequestException {

    public ClubBadRequestException(String message) {
        super(message);
    }
}
