package com.cruru.email.exception;

import com.cruru.advice.InternalServerException;

public class EmailAttachmentsException extends InternalServerException {

    private static final String MESSAGE = "첨부 파일을 정상적으로 처리하지 못했습니다. 발송자 Id: %d, 메일 제목: %s";

    public EmailAttachmentsException(Long from, String subject) {
        super(String.format(MESSAGE, from, subject));
    }
}
