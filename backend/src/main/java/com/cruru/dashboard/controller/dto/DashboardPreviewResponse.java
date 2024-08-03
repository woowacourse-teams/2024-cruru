package com.cruru.dashboard.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record DashboardPreviewResponse(
        @JsonProperty(value = "dashboard_id")
        long id,

        String title,

        Stats stats,

        @JsonProperty(value = "post_url")
        String url,

        @JsonProperty(value = "end_date")
        @JsonFormat(shape = Shape.STRING)
        LocalDateTime endDate
) {

}
