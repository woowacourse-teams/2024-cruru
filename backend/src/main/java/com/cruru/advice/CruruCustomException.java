package com.cruru.advice;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CruruCustomException extends RuntimeException {

    private final HttpStatus status;

    public CruruCustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public String statusCode() {
        return status.toString();
    }

    public HttpStatus getStatus() {
        return status;
    }
}
