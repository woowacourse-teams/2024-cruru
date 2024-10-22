package com.cruru.dashboard.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;

public record DashboardPreviewResponse(

        long dashboardId,

        String applyFormId,

        String title,

        StatsResponse stats,

        @JsonFormat(shape = Shape.STRING)
        LocalDateTime startDate,

        @JsonFormat(shape = Shape.STRING)
        LocalDateTime endDate
) {

    public static DashboardPreviewResponse of(
            long dashboardId,
            long applyFormId,
            String title,
            StatsResponse stats,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        return new DashboardPreviewResponse(dashboardId, String.valueOf(applyFormId), title, stats, startDate, endDate);
    }
}
