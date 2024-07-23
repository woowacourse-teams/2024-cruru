package com.cruru.club.exception;

import com.cruru.advice.BadRequestException;

public class ClubNameLengthException extends BadRequestException {

    public static final String MESSAGE = "동아리 이름이 비어있거나 %d자 초과입니다. 현재 이름 글자수: %d";

    public ClubNameLengthException(int maxLength, int currentLength) {
        super(String.format(MESSAGE, maxLength, currentLength));
    }
}
