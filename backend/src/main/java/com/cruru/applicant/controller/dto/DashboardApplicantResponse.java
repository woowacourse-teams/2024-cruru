package com.cruru.applicant.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record DashboardApplicantResponse(
        long applicantId,

        @JsonProperty("applicantName")
        String name,

        LocalDateTime createdAt,

        Boolean isRejected,

        int evaluationCount
) {

}
