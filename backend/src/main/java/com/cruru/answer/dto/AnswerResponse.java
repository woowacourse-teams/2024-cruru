package com.cruru.answer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AnswerResponse(
        @JsonProperty("orderIndex")
        int sequence,

        String question,

        String answer
) {

}
