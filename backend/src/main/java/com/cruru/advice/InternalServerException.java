package com.cruru.advice;

import org.springframework.http.HttpStatus;

public class InternalServerException extends CruruCustomException {

    private static final String TEXT = "서버 내부에 오류가 발생했습니다.";
    private static final String STATUS_CODE = HttpStatus.INTERNAL_SERVER_ERROR.toString();

    public InternalServerException() {
        super(TEXT, STATUS_CODE);
    }
}
