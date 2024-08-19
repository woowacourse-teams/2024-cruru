package com.cruru.dashboard.controller.dto;

import com.cruru.question.controller.dto.QuestionCreateRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public record DashboardCreateRequest(
        @NotBlank(message = "공고 제목은 필수 값입니다.")
        String title,

        String postingContent,

        @Valid
        List<QuestionCreateRequest> questions,

        @NotNull(message = "시작 날짜는 필수 값입니다.")
        @JsonFormat(shape = Shape.STRING)
        LocalDateTime startDate,

        @NotNull(message = "종료 날짜는 필수 값입니다.")
        @JsonFormat(shape = Shape.STRING)
        LocalDateTime endDate
) {

}
