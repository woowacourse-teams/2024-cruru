package com.cruru.club.exception;

import com.cruru.advice.BadRequestException;

public class ClubNameCharacterException extends BadRequestException {

    public static final String MESSAGE = "동아리 이름에 \\ 혹은 |를 포함하고 있습니다. 입력된 이름: %s";

    public ClubNameCharacterException(String invalidName) {
        super(String.format(MESSAGE, invalidName));
    }
}
