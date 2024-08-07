package com.cruru.dashboard.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;

public record DashboardPreviewResponse(

        long dashboardId,

        String title,

        StatsResponse stats,

        String postUrl,

        @JsonFormat(shape = Shape.STRING)
        LocalDateTime endDate
) {

}
