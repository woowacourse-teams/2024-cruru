package com.cruru.question.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AnswerResponse(
        @JsonProperty("orderIndex")
        int sequence,

        String question,

        String answer
) {

}
