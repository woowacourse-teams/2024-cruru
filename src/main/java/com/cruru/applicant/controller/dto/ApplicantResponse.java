package com.cruru.applicant.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record ApplicantResponse(
        @JsonProperty(value = "applicant_id")
        Long id,

        String name,

        String email,

        String phone,

        @JsonProperty("created_at")
        LocalDateTime createdAt
) {

}
