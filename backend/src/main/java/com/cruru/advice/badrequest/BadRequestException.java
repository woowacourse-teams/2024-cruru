package com.cruru.advice.badrequest;

import com.cruru.advice.CruruCustomException;

public class BadRequestException extends CruruCustomException {

    private static final String STATUS_CODE = "404";

    public BadRequestException(String message) {
        super(message, STATUS_CODE);
    }
}
