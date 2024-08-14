package com.cruru.evaluation.controller.dto;

import java.time.LocalDateTime;

public record EvaluationResponse(
        long evaluationId,

        int score,

        String content,

        LocalDateTime createdDate
) {

}
