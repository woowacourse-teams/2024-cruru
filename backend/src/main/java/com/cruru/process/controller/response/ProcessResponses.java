package com.cruru.process.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public record ProcessResponses(

        long applyFormId,

        @JsonProperty("processes")
        List<ProcessResponse> processResponses,

        String title,

        LocalDateTime startDate,

        LocalDateTime endDate
) {

}
