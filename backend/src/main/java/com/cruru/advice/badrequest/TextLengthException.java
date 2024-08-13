package com.cruru.advice.badrequest;

public class TextLengthException extends BadRequestException {

    private static final String MESSAGE_NOTICE_MAX = "%s은(는) 최대 %d자입니다. 현재 글자수: %d";
    private static final String MESSAGE_NOTICE_MIN_MAX = "%s은(는) 최소 %d, 최대 %d자입니다. 현재 글자수: %d";

    public TextLengthException(String text, int maxLength, int currentLength) {
        super(String.format(MESSAGE_NOTICE_MAX, text, currentLength, maxLength));
    }

    public TextLengthException(String text, int minLength, int maxLength, int currentLength) {
        super(String.format(MESSAGE_NOTICE_MIN_MAX, text, minLength, maxLength, currentLength));
    }
}
