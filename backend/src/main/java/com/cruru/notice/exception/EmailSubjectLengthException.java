package com.cruru.notice.exception;

import com.cruru.advice.badrequest.TextLengthException;

public class EmailSubjectLengthException extends TextLengthException {

    private static final String TEXT = "email";

    public EmailSubjectLengthException(int maxLength, int currentLength) {
        super(TEXT, maxLength, currentLength);
    }
}
