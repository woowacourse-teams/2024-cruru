package com.cruru.club.exception;

import com.cruru.advice.badrequest.TextBlankException;

public class ClubNameBlankException extends TextBlankException {

    private static final String TEXT = "동아리 이름";

    public ClubNameBlankException() {
        super(TEXT);
    }
}
