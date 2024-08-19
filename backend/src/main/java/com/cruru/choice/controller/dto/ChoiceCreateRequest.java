package com.cruru.choice.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ChoiceCreateRequest(
        @NotBlank(message = "객관식 질문의 선택지는 필수 값입니다.")
        String choice,

        @NotNull
        @PositiveOrZero
        Integer orderIndex
) {

}
