package com.cruru.util.fixture;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.dashboard.domain.Dashboard;

public class ApplyFormFixture {

    public static ApplyForm backend(Dashboard backendDashboard) {
        return new ApplyForm(
                "크루루 백엔드 모집 공고",
                "# 모집공고 설명1 ## 이렇게 지원하세요",
                "www.cruru.kr/post/" + backendDashboard.getId(),
                LocalDateFixture.oneDayAgo(),
                LocalDateFixture.oneWeekLater(),
                backendDashboard
        );
    }

    public static ApplyForm backend() {
        return new ApplyForm(
                "크루루 백엔드 모집 공고",
                "# 모집공고 설명1 ## 이렇게 지원하세요",
                "www.cruru.kr/post/" + 1,
                LocalDateFixture.oneDayAgo(),
                LocalDateFixture.oneWeekLater(),
                null
        );
    }

    public static ApplyForm frontend(Dashboard frontendDashboard) {
        return new ApplyForm(
                "크루루 프론트엔드 모집 공고",
                "# 모집공고 설명2 ## 이렇게 지원하세요",
                "www.cruru.kr/post/" + frontendDashboard.getId(),
                LocalDateFixture.oneDayAgo(),
                LocalDateFixture.oneWeekLater(),
                frontendDashboard
        );
    }
}
