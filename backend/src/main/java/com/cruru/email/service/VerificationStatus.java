package com.cruru.email.service;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum VerificationStatus {

    VERIFIED("verified"),
    NOT_VERIFIED("not_verified"),
    ;

    private final String value;

    VerificationStatus(String value) {
        this.value = value;
    }

    public static VerificationStatus fromValue(String value) {
        return Arrays.stream(VerificationStatus.values())
                .filter(status -> status.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElse(NOT_VERIFIED);
    }

    public boolean isVerified() {
        return this == VERIFIED;
    }
}
