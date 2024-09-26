package com.cruru.applyform.exception.badrequest;

import com.cruru.advice.badrequest.BadRequestException;
import java.time.LocalDateTime;

public class StartDateAfterEndDateException extends BadRequestException {

    private static final String TEXT = "접수 시작일 (%s)이 마감일 (%s)보다 늦을 수 없습니다.";

    public StartDateAfterEndDateException(LocalDateTime startDate, LocalDateTime endDate) {
        super(String.format(TEXT, startDate, endDate));
    }
}
