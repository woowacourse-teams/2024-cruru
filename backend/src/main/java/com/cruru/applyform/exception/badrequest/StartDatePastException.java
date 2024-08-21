package com.cruru.applyform.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;
import java.time.LocalDate;

public class StartDatePastException extends BadRequestException {

    private static final String TEXT = "접수 시작일 (%s)이 현재 날짜 (%s)보다 이전일 수 없습니다.";

    public StartDatePastException(LocalDate startDate, LocalDate now) {
        super(String.format(TEXT, startDate, now));
    }
}
