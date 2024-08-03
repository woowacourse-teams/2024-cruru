package com.cruru.dashboard.controller.dto;

import java.util.List;

public record DashboardPreviewResponses(List<DashboardPreviewResponse> dashboardPreviewResponses) {

    public DashboardPreviewResponses(List<DashboardPreviewResponse> dashboardPreviewResponses) {
        this.dashboardPreviewResponses = dashboardPreviewResponses.stream()
                .distinct()
                .toList();
    }
}
