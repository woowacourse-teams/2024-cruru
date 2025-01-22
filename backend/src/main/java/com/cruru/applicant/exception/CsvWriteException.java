package com.cruru.applicant.exception;

import com.cruru.advice.InternalServerException;

public class CsvWriteException extends InternalServerException {
    private static final String MESSAGE = "CSV 파일 쓰기를 실패했습니다.";

    public CsvWriteException() {
        super(String.format(MESSAGE));
    }
}
