package com.cruru.choice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChoiceResponse(
        long id,

        @JsonProperty("label")
        String content,

        int orderIndex
) {

}
