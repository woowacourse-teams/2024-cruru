package com.cruru.advice;

@FunctionalInterface
public interface ExceptionCallback {

    void handleException(Exception e);
}
