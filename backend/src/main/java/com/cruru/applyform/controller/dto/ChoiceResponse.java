package com.cruru.applyform.controller.dto;

public record ChoiceResponse(
        long id,

        @JsonProperty("label")
        String content,

        int orderIndex
) {

}
