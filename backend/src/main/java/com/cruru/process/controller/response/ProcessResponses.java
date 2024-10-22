package com.cruru.process.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public record ProcessResponses(

        String applyFormId,

        @JsonProperty("processes")
        List<ProcessResponse> processResponses,

        String title,

        LocalDateTime startDate,

        LocalDateTime endDate
) {

}
