package com.cruru.email.service;

public enum VerificationStatus {
    VERIFIED,
    NOT_VERIFIED,
    ;

    public static VerificationStatus fromString(String status) {
        try {
            return VerificationStatus.valueOf(status);
        } catch (IllegalArgumentException | NullPointerException e) {
            return NOT_VERIFIED;
        }
    }

    public boolean isVerified() {
        return this == VERIFIED;
    }
}
