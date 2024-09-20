package com.cruru.advice.badrequest;

import com.cruru.advice.CruruCustomException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends CruruCustomException {

    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message) {
        super(message, STATUS);
    }
}
