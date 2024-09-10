package com.cruru.advice;

public class ForbiddenException extends CruruCustomException {

    private static final String MESSAGE = "접근 권한이 없습니다";
    private static final String STATUS_CODE = "403";

    public ForbiddenException() {
        super(MESSAGE, STATUS_CODE);
    }
}
