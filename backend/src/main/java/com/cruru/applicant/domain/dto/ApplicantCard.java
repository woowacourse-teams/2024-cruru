package com.cruru.applicant.domain.dto;

import com.cruru.applicant.controller.response.ApplicantCardResponse;
import java.time.LocalDateTime;

public record ApplicantCard(
        long id,

        String name,

        LocalDateTime createdAt,

        Boolean isRejected,

        long evaluationCount,

        double averageScore
) {

    public ApplicantCardResponse toResponse() {
        return new ApplicantCardResponse(id, name, createdAt, isRejected, (int) evaluationCount, averageScore);
    }
}
