package com.cruru.email.exception;

import com.cruru.advice.CruruCustomException;
import org.springframework.http.HttpStatus;

public class EmailRedisException extends CruruCustomException {

    public EmailRedisException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
