package com.cruru.applicant.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ApplicantDetailResponse(
        @JsonProperty("details")
        List<QnaResponse> qnaResponses
) {

}
