package com.cruru.applicant.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record ApplicantCardResponse(
        @JsonProperty("applicantId")
        long id,

        @JsonProperty("applicantName")
        String name,

        LocalDateTime createdAt,

        Boolean isRejected,

        int evaluationCount,

        double averageScore
) {

}
