package com.cruru.dashboard.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Stats(
        int accept,

        int fail,

        @JsonProperty(value = "in_progress")
        int inProgress,

        int total
) {

}
