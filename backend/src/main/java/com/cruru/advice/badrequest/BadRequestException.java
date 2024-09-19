package com.cruru.advice.badrequest;

import com.cruru.advice.CruruCustomException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends CruruCustomException {

    private static final String STATUS_CODE = HttpStatus.BAD_REQUEST.toString();

    public BadRequestException(String message) {
        super(message, STATUS_CODE);
    }
}
