package com.cruru.applyform.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ApplyFormWriteRequest(
        @NotBlank(message = "제목은 필수 값입니다.")
        String title,

        String postingContent,

        @NotNull(message = "시작 날짜는 필수 값입니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        LocalDateTime startDate,

        @NotNull(message = "종료 날짜는 필수 값입니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        LocalDateTime endDate
) {

}
