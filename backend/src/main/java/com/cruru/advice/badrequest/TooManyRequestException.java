package com.cruru.advice.badrequest;

import com.cruru.advice.CruruCustomException;
import org.springframework.http.HttpStatus;

public class TooManyRequestException extends CruruCustomException {

    public TooManyRequestException(String message) {
        super(message, HttpStatus.TOO_MANY_REQUESTS);
    }
}
