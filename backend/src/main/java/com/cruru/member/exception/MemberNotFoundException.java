package com.cruru.member.exception;

import com.cruru.advice.NotFoundException;

public class MemberNotFoundException extends NotFoundException {

    private static final String TARGET = "회원";

    public MemberNotFoundException() {
        super(TARGET);
    }
}
