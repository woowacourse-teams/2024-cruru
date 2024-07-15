package com.cruru.dashboard.exception;

import com.cruru.advice.NotFoundException;

public class DashboardNotFoundException extends NotFoundException {

    private static final String message = "";

    public DashboardNotFoundException() {
        super(message);
    }
}
