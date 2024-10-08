package com.cruru.applicant.controller.response;

import com.cruru.process.controller.response.ProcessSimpleResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ApplicantBasicResponse(
        @JsonProperty("applicant")
        ApplicantResponse applicantResponse,

        @JsonProperty("process")
        ProcessSimpleResponse processResponse
) {

}
