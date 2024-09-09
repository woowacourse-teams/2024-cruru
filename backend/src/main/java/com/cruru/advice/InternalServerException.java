package com.cruru.advice;

public class InternalServerException extends RuntimeException {

    private static final String TEXT = "서버 내부에 오류가 발생했습니다.";

    public InternalServerException() {
        super(TEXT);
    }

    public InternalServerException(String text) {
        super(text);
    }
}
