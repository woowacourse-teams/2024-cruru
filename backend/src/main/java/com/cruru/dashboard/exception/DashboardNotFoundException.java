package com.cruru.dashboard.exception;

import com.cruru.advice.NotFoundException;

public class DashboardNotFoundException extends NotFoundException {

    private static final String TARGET = "대시보드";

    public DashboardNotFoundException() {
        super(TARGET);
    }
}
