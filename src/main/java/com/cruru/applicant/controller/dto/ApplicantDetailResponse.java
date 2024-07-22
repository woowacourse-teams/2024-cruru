package com.cruru.applicant.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ApplicantDetailResponse(
        @JsonProperty("applicant_name")
        String applicantName,

        @JsonProperty("dashboard_name")
        String dashboardName,

        @JsonProperty("details")
        List<QnaResponse> qnaResponses
) {

}
