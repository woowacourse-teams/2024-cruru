package com.cruru.email.exception;

import com.cruru.advice.badrequest.TextLengthException;

public class EmailContentLengthException extends TextLengthException {

    private static final String TEXT = "email 본문";

    public EmailContentLengthException(int maxLength, int currentLength) {
        super(TEXT, maxLength, currentLength);
    }
}
