package com.cruru.process.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ProcessResponses(

        String postUrl,

        @JsonProperty("processes")
        List<ProcessResponse> processResponses,

        String title
) {

}
