package com.cruru.util.fixture;

import com.cruru.dashboard.domain.Dashboard;
import com.cruru.notice.domain.Notice;
import com.cruru.notice.domain.NoticeType;

public class NoticeFixture {

    public static Dashboard backend() {
        return new Dashboard(null);
    }

    public static Notice approveNotice() {
        return new Notice(
                null,
                null,
                "[우아한테크코스] 7기 최종 심사 결과 안내",
                "우아한테크코스 합격을 진심으로 축하합니다!",
                NoticeType.EMAIL
        );
    }

    public static Notice rejectNotice() {
        return new Notice(
                null,
                null,
                "[우아한테크코스] 7기 최종 심사 결과 안내",
                "지원해주셔서 감사합니다. 불합격입니다.",
                NoticeType.EMAIL
        );
    }
}
