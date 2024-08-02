package com.cruru.choice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChoiceResponse(
        long id,

        String content,

        @JsonProperty("order_index")
        int orderIndex
) {

}
