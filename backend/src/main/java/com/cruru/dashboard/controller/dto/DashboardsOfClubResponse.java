package com.cruru.dashboard.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DashboardsOfClubResponse(
        @JsonProperty(value = "club_name")
        String clubName,

        @JsonProperty(value = "dashboards")
        DashboardPreviewResponses dashboardPreviewResponses
) {

}
