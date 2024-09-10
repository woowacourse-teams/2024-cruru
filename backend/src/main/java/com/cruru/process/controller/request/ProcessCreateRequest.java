package com.cruru.process.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProcessCreateRequest(
        @NotBlank(message = "프로세스 이름은 필수 값입니다.")
        @JsonProperty("processName")
        String name,

        @NotBlank(message = "프로세스 설명은 필수 값입니다.")
        String description,

        @NotNull(message = "프로세스 순서는 필수 값입니다.")
        @PositiveOrZero(message = "프로세스 순서는 0 이상의 정수입니다.")
        @JsonProperty("orderIndex")
        Integer sequence
) {

}
