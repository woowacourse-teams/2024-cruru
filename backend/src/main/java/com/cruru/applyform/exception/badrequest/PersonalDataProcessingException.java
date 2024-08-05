package com.cruru.applyform.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;

public class PersonalDataProcessingException extends BadRequestException {

    private static final String TEXT = "개인 정보 수집에 동의하지 않았습니다.";

    public PersonalDataProcessingException() {
        super(TEXT);
    }
}
