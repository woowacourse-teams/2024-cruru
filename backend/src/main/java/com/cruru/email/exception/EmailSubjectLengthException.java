package com.cruru.email.exception;

import com.cruru.advice.badrequest.TextLengthException;

public class EmailSubjectLengthException extends TextLengthException {

    private static final String TEXT = "email 제목";

    public EmailSubjectLengthException(int maxLength, int currentLength) {
        super(TEXT, maxLength, currentLength);
    }
}
