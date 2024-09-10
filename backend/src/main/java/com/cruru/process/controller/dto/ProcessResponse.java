package com.cruru.process.controller.dto;

import com.cruru.applicant.controller.response.ApplicantCardResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ProcessResponse(
        @JsonProperty("processId")
        long id,

        @JsonProperty("orderIndex")
        int sequence,

        String name,

        String description,

        @JsonProperty("applicants")
        List<ApplicantCardResponse> applicantCardResponses
) {

}
