package com.cruru.member.exception.badrequest;

import com.cruru.advice.badrequest.TextLengthException;

public class MemberPasswordLengthException extends TextLengthException {

    private static final String MESSAGE = "비밀번호";

    public MemberPasswordLengthException(int min, int max) {
        super(MESSAGE, min, max);
    }
}
