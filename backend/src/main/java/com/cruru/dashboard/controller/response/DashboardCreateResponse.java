package com.cruru.dashboard.controller.response;

public record DashboardCreateResponse(
        String applyFormId,
        long dashboardId
) {

    public static DashboardCreateResponse of(long applyFormId, long dashboardId) {
        return new DashboardCreateResponse(String.valueOf(applyFormId), dashboardId);
    }
}
