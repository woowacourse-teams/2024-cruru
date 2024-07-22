package com.cruru.evaluation.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record EvaluationsResponse(
        @JsonProperty("evaluations")
        List<EvaluationResponse> evaluationsResponse
) {

}
