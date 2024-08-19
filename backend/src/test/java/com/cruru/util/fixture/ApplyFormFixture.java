package com.cruru.util.fixture;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.dashboard.domain.Dashboard;
import java.time.LocalDateTime;

public class ApplyFormFixture {

    public static ApplyForm backend(Dashboard backendDashboard) {
        return new ApplyForm(
                "크루루 백엔드 모집 공고",
                "# 모집공고 설명1 ## 이렇게 지원하세요",
                "www.cruru.kr/post/" + backendDashboard.getId(),
                LocalDateTime.of(2099, 11, 30, 23, 59, 59),
                LocalDateTime.of(2099, 12, 31, 23, 59, 59),
                backendDashboard
        );
    }

    public static ApplyForm frontend(Dashboard frontendDashboard) {
        return new ApplyForm(
                "크루루 프론트엔드 모집 공고",
                "# 모집공고 설명2 ## 이렇게 지원하세요",
                "www.cruru.kr/post/" + frontendDashboard.getId(),
                LocalDateTime.of(2099, 12, 11, 23, 59, 59),
                LocalDateTime.of(2099, 12, 21, 23, 59, 59),
                frontendDashboard
        );
    }
}
