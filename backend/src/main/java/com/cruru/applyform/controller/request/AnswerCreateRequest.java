package com.cruru.applyform.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;

public record AnswerCreateRequest(
        @NotNull(message = "질문 식별자는 필수 값입니다.")
        @PositiveOrZero(message = "질문 식별자는 0 이상의 정수입니다.")
        Long questionId,

        @NotNull(message = "응답은 필수 값입니다.")
        List<String> replies
) {

}
