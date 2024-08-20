package com.cruru.applyform.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record ApplyFormRequest(
        @NotBlank(message = "제목은 필수 값입니다.")
        String title,

        String postingContent,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        LocalDateTime startDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        LocalDateTime endDate
) {

}
