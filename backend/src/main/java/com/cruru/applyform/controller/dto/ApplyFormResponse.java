package com.cruru.applyform.controller.dto;

import com.cruru.question.controller.dto.QuestionResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public record ApplyFormResponse(
        String title,

        @JsonProperty("postingContent")
        String description,

        LocalDateTime startDate,

        LocalDateTime endDate,

        @JsonProperty("questions")
        List<QuestionResponse> questionResponses
) {

}
