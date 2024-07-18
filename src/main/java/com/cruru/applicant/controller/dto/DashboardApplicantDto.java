package com.cruru.applicant.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record DashboardApplicantDto(
        @JsonProperty("applicant_id")
        long applicantId,

        @JsonProperty("applicant_name")
        String name,

        @JsonProperty("created_at")
        LocalDateTime createdAt,

        @JsonProperty("is_rejected")
        Boolean isRejected
) {

}
