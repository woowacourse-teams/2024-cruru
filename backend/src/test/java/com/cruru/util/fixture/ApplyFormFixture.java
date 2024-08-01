package com.cruru.util.fixture;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.dashboard.domain.Dashboard;
import java.time.LocalDateTime;

public class ApplyFormFixture {

    public static ApplyForm createBackendApplyForm(Dashboard backendDashboard) {
        return new ApplyForm(
                "크루루 백엔드 모집 공고",
                "# 모집공고 설명1 ## 이렇게 지원하세요",
                "www.cruru.kr/example/recruit/apply/1",
                LocalDateTime.MIN,
                LocalDateTime.MAX,
                backendDashboard
        );
    }

    public static ApplyForm createFrontendApplyForm(Dashboard frontendDashboard) {
        return new ApplyForm(
                "크루루 프론트엔드 모집 공고",
                "# 모집공고 설명2 ## 이렇게 지원하세요",
                "www.cruru.kr/example/recruit/apply/2",
                LocalDateTime.MIN,
                LocalDateTime.MAX,
                frontendDashboard
        );
    }
}
