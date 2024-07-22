package com.cruru.evaluation.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EvaluationResponse(
        @JsonProperty("evaluation_id")
        long evaluationId,

        int score,

        String content
) {

}
