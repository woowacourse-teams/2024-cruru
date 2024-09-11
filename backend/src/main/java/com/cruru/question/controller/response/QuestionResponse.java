package com.cruru.question.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record QuestionResponse(
        long id,

        String type,

        @JsonProperty("label")
        String content,

        int orderIndex,

        @JsonProperty("choices")
        List<ChoiceResponse> choiceResponses,

        boolean required
) {

}
