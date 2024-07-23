package com.cruru.process.exception;

import com.cruru.advice.NotFoundException;

public class ProcessNotFoundException extends NotFoundException {

    private static final String TARGET = "프로세스";

    public ProcessNotFoundException() {
        super(TARGET);
    }
}
