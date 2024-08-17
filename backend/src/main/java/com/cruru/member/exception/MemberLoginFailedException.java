package com.cruru.member.exception;

import com.cruru.advice.UnauthorizedException;

public class MemberLoginFailedException extends UnauthorizedException {

    private static final String MESSAGE = "이메일 또는 비밀번호가 일치하지 않습니다.";

    public MemberLoginFailedException() {
        super(MESSAGE);
    }
}
