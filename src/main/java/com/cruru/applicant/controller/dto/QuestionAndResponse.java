package com.cruru.applicant.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record QuestionAndResponse(
        @JsonProperty("order_index")
        Integer sequence,

        String question,

        String response
) {

}
