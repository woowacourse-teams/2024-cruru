package com.cruru.util.fixture;

import com.cruru.dashboard.domain.Dashboard;

public class DashboardFixture {

    public static Dashboard createBackendDashboard() {
        return new Dashboard(null);
    }

    public static Dashboard createFrontendDashboard() {
        return new Dashboard(null);
    }
}
