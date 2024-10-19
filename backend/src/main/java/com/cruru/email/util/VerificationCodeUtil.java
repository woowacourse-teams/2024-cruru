package com.cruru.email.util;

import java.util.Random;

public class VerificationCodeUtil {

    private static final Random random = new Random();
    private static final int CODE_LENGTH = 6;

    private VerificationCodeUtil() {
    }

    public static String generateVerificationCode() {
        return String.format("%0" + CODE_LENGTH + "d", random.nextInt((int) Math.pow(10, CODE_LENGTH)));
    }
}
