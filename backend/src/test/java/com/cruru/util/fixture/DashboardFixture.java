package com.cruru.util.fixture;

import com.cruru.club.domain.Club;
import com.cruru.dashboard.domain.Dashboard;

public class DashboardFixture {

    public static Dashboard createBackendDashboard() {
        return new Dashboard(null);
    }

    public static Dashboard createBackendDashboard(Club club) {
        return new Dashboard(club);
    }

    public static Dashboard createFrontendDashboard() {
        return new Dashboard(null);
    }
}
