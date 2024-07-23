package com.cruru.club.exception;

import com.cruru.advice.badrequest.TextLengthException;

public class ClubNameLengthException extends TextLengthException {

    private static final String TEXT = "동아리 이름";

    public ClubNameLengthException(int maxLength, int currentLength) {
        super(TEXT, maxLength, currentLength);
    }
}
