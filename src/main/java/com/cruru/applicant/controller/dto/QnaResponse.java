package com.cruru.applicant.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record QnaResponse(
        @JsonProperty("order_index")
        Integer sequence,

        String question,

        String answer
) {

}
