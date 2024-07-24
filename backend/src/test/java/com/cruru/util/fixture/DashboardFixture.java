package com.cruru.util.fixture;

import com.cruru.dashboard.domain.Dashboard;

public class DashboardFixture {

    public static Dashboard createBackendDashboard() {
        return new Dashboard("백엔드 7기 모집", null);
    }

    public static Dashboard createFrontendDashboard() {
        return new Dashboard("프론트엔드 7기 모집", null);
    }
}
