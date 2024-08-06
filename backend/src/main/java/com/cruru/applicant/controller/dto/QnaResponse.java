package com.cruru.applicant.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record QnaResponse(
        @JsonProperty("orderIndex")
        int sequence,

        String question,

        String answer
) {

}
