package com.cruru.util.fixture;

import com.cruru.dashboard.domain.Dashboard;
import com.cruru.email.domain.Email;

public class EmailFixture {

    public static final String SUBJECT = "[우아한테크코스] 7기 최종 심사 결과 안내";
    public static final String APPROVE_CONTENT = "우아한테크코스 합격을 진심으로 축하합니다!";
    public static final String REJECT_CONTENT = "지원해주셔서 감사합니다. 불합격입니다.";

    public static Dashboard backend() {
        return new Dashboard(null);
    }

    public static Email approveEmail() {
        return new Email(
                null,
                null,
                SUBJECT,
                APPROVE_CONTENT
        );
    }

    public static Email rejectEmail() {
        return new Email(
                null,
                null,
                SUBJECT,
                REJECT_CONTENT
        );
    }
}
