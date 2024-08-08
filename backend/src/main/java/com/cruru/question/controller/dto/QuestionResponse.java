package com.cruru.question.controller.dto;

import com.cruru.choice.controller.dto.ChoiceResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record QuestionResponse(
        long id,

        String type,

        String content,

        String description,

        int orderIndex,

        @JsonProperty("choices")
        List<ChoiceResponse> choiceResponses,

        boolean required
) {

}
