package com.cruru.choice.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ChoiceCreateRequest(
        @NotBlank(message = "객관식 질문의 선택지는 필수 값입니다.")
        String choice,

        @NotNull(message = "객관식 질문의 순서는 필수 값입니다.")
        @PositiveOrZero(message = "객관식 질문의 순서는 0이거나 양의 정수여야 합니다.")
        Integer orderIndex
) {

}
