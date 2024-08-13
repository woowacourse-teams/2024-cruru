package com.cruru.member.exception.badrequest;

import com.cruru.advice.badrequest.TextLengthException;

public class MemberPasswordLengthException extends TextLengthException {

    private static final String TEXT = "비밀번호";

    public MemberPasswordLengthException(int min, int max, int currentLength) {
        super(TEXT, min, max, currentLength);
    }
}
