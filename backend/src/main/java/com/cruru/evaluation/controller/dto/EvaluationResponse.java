package com.cruru.evaluation.controller.dto;

public record EvaluationResponse(
        long evaluationId,

        int score,

        String content
) {

}
