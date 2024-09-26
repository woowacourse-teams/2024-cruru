package com.cruru.advice;

import org.springframework.http.HttpStatus;

public class InternalServerException extends CruruCustomException {

    private static final String TEXT = "서버 내부에 오류가 발생했습니다.";
    private static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public InternalServerException() {
        super(TEXT, STATUS);
    }

    public InternalServerException(String text) {
        super(text, STATUS);
    }
}
