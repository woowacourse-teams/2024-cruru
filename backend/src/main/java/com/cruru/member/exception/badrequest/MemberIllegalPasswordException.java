package com.cruru.member.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class MemberIllegalPasswordException extends BadRequestException {

    private static final String MESSAGE = "입력하신 비밀번호의 형식이 맞지 않습니다.";

    public MemberIllegalPasswordException() {
        super(MESSAGE);
    }
}
