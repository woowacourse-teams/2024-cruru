package com.cruru.applyform.controller.dto;

import com.cruru.applicant.controller.dto.ApplicantCreateRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ApplyFormSubmitRequest(
        @JsonProperty("applicant")
        ApplicantCreateRequest applicantCreateRequest,

        @JsonProperty("answers")
        List<AnswerCreateRequest> answerCreateRequest,

        @JsonProperty("personal_data_collection")
        Boolean personalDataCollection
) {

}
