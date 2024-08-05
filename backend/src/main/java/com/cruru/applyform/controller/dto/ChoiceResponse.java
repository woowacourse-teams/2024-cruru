package com.cruru.applyform.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChoiceResponse(
        long id,

        @JsonProperty("label")
        String content,

        @JsonProperty("order_index")
        int orderIndex
) {

}
