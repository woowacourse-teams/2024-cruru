package com.cruru.dashboard.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StatsResponse(
        int accept,

        int fail,

        @JsonProperty(value = "in_progress")
        int inProgress,

        int total
) {

}
