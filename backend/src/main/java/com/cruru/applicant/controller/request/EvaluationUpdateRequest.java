package com.cruru.applicant.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EvaluationUpdateRequest(
        @NotNull(message = "평가 점수는 필수 값입니다.")
        @Positive(message = "평가 점수는 1 이상 5 이하의 정수입니다.")
        Integer score,

        @NotNull(message = "평가 내용은 필수 값입니다.")
        String content
) {

}
