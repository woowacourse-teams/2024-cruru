package com.cruru.process.exception;

import com.cruru.advice.BadRequestException;

public class ProcessBadRequestException extends BadRequestException {

    public ProcessBadRequestException(String message) {
        super(message);
    }
}
