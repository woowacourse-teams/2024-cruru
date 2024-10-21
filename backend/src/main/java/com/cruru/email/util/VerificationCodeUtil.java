package com.cruru.email.util;

import com.cruru.email.exception.badrequest.VerificationCodeMismatchException;
import com.cruru.email.exception.badrequest.VerificationCodeNotFoundException;
import java.util.Random;

public class VerificationCodeUtil {

    private static final Random random = new Random();
    private static final int CODE_LENGTH = 6;

    private VerificationCodeUtil() {
    }

    public static String generateVerificationCode() {
        return String.format("%0" + CODE_LENGTH + "d", random.nextInt((int) Math.pow(10, CODE_LENGTH)));
    }

    public static void verify(String storedVerificationCode, String inputVerificationCode) {
        if (storedVerificationCode == null) {
            throw new VerificationCodeNotFoundException();
        }

        if (!storedVerificationCode.equals(inputVerificationCode)) {
            throw new VerificationCodeMismatchException();
        }
    }
}
