package com.cruru.applicant.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;

public record EvaluationResponse(
        long evaluationId,

        int score,

        String content,

        @JsonFormat(shape = Shape.STRING)
        LocalDateTime createdDate
) {

}
