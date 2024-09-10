package com.cruru.applicant.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record EvaluationResponses(
        @JsonProperty("evaluations")
        List<EvaluationResponse> evaluationsResponse
) {

}
