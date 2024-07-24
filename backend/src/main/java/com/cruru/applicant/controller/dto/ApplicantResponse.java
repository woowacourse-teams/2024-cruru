package com.cruru.applicant.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record ApplicantResponse(
        @JsonProperty("applicant_id")
        long id,

        String name,

        String email,

        String phone,

        @JsonProperty("process_name")
        String processName,

        @JsonProperty("created_at")
        LocalDateTime createdAt
) {

}
