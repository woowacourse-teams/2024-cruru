package com.cruru.applyform.controller.dto;

import com.cruru.question.controller.dto.QuestionResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public record ApplyFormResponse(
        String title,

        @JsonProperty("posting_content")
        String description,

        @JsonProperty("start_date")
        LocalDateTime startDate,

        @JsonProperty("end_date")
        LocalDateTime endDate,

        @JsonProperty("questions")
        List<QuestionResponse> questionResponses
) {

}
