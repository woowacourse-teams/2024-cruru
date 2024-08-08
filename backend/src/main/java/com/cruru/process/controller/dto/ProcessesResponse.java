package com.cruru.process.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ProcessesResponse(
        String title,

        @JsonProperty("postUrl")
        String url,

        @JsonProperty("processes")
        List<ProcessResponse> processResponses
) {

}
