package com.cruru.applyform.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record AnswerCreateRequest(
        @JsonProperty("question_id")
        Long questionId,

        List<String> choices
) {

}
