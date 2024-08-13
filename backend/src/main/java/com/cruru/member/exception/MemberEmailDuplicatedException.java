package com.cruru.member.exception;

import com.cruru.advice.ConflictException;

public class MemberEmailDuplicatedException extends ConflictException {

    private static final String MESSAGE = "이미 가입되어있는 Email 입니다.";

    public MemberEmailDuplicatedException() {
        super(MESSAGE);
    }
}
