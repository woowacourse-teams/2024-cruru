package com.cruru.member.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class MemberIllegalPhoneNumberException extends BadRequestException {

    private static final String MESSAGE = "올바르지 않은 전화번호 형식입니다.";

    public MemberIllegalPhoneNumberException() {
        super(MESSAGE);
    }
}
