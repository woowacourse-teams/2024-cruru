package com.cruru.question.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import java.util.List;

public record QuestionUpdateRequests(
        @JsonProperty("questions")
        @Valid
        List<QuestionCreateRequest> questions
) {

}
