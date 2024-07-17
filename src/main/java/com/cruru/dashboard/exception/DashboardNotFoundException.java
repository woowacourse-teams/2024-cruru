package com.cruru.dashboard.exception;

import com.cruru.advice.NotFoundException;

public class DashboardNotFoundException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 대시보드입니다.";

    public DashboardNotFoundException() {
        super(MESSAGE);
    }
}
