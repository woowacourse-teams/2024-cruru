package com.cruru.applyform.exception;

import com.cruru.advice.NotFoundException;

public class ApplyFormNotFoundException extends NotFoundException {

    private static final String TEXT = "지원서 폼";

    public ApplyFormNotFoundException() {
        super(TEXT);
    }
}
