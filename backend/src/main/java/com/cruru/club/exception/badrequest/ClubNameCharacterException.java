package com.cruru.club.exception.badrequest;

import com.cruru.advice.badrequest.TextCharacterException;

public class ClubNameCharacterException extends TextCharacterException {

    private static final String TEXT = "동아리 이름";

    public ClubNameCharacterException(String invalidText) {
        super(TEXT, invalidText);
    }
}
