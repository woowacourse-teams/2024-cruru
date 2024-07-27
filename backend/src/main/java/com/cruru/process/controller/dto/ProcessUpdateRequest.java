package com.cruru.process.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProcessUpdateRequest(
        @NotBlank(message = "수정할 프로세스 이름은 필수 값입니다.")
        @JsonProperty("process_name")
        String name,

        @NotNull(message = "수정할 프로세스 설명은 필수 값입니다.")
        String description
) {

}
