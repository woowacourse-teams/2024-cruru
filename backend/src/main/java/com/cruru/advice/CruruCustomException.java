package com.cruru.advice;

import lombok.Getter;

@Getter
public class CruruCustomException extends RuntimeException {

    private final String statusCode;

    public CruruCustomException(String message, String statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
