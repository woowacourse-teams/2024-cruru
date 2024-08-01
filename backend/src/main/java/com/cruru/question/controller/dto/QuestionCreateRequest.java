package com.cruru.question.controller.dto;

import com.cruru.choice.controller.dto.ChoiceCreateRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record QuestionCreateRequest(
        @NotBlank(message = "질문 유형은 필수 값입니다.")
        String type,

        @NotBlank(message = "질문 내용은 필수 값입니다.")
        String question,

        String description,

        List<ChoiceCreateRequest> choices,

        @JsonProperty("order_index")
        int orderIndex
) {

}
