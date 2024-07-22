package com.cruru.member.exception;

import com.cruru.advice.NotFoundException;

public class MemberNotFoundException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 회원입니다.";

    public MemberNotFoundException() {
        super(MESSAGE);
    }
}
