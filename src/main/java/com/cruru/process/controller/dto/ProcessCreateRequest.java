package com.cruru.process.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProcessCreateRequest(
        @JsonProperty("process_name")
        String name,

        String description,

        @JsonProperty("prior_process_id")
        Long priorProcessId
) {
}
