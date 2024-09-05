package com.cruru.dashboard.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;

public record DashboardPreviewResponse(

        long dashboardId,

        long applyFormId,

        String title,

        StatsResponse stats,

        @JsonFormat(shape = Shape.STRING)
        LocalDateTime startDate,

        @JsonFormat(shape = Shape.STRING)
        LocalDateTime endDate
) {

}
