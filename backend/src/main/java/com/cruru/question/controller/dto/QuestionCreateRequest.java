package com.cruru.question.controller.dto;

import com.cruru.choice.controller.dto.ChoiceCreateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;

public record QuestionCreateRequest(
        @NotBlank(message = "질문 유형은 필수 값입니다.")
        String type,

        @NotBlank(message = "질문 내용은 필수 값입니다.")
        String question,

        @Valid
        List<ChoiceCreateRequest> choices,

        @NotNull
        @PositiveOrZero
        Integer orderIndex,

        @NotNull
        Boolean required
) {

}
