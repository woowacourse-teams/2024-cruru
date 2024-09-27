package com.cruru.util.fixture;

import com.cruru.club.domain.Club;
import com.cruru.dashboard.domain.Dashboard;

public class DashboardFixture {

    public static Dashboard backend() {
        return new Dashboard(null);
    }

    public static Dashboard backend(Club club) {
        return new Dashboard(club);
    }

    public static Dashboard frontend() {
        return new Dashboard(null);
    }

    public static Dashboard frontend(Club club) {
        return new Dashboard(club);
    }
}
