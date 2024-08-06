package com.cruru.applyform.controller.dto;

import com.cruru.applicant.controller.dto.ApplicantCreateRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ApplyFormSubmitRequest(
        @JsonProperty("applicant")
        ApplicantCreateRequest applicantCreateRequest,

        @JsonProperty("answers")
        List<AnswerCreateRequest> answerCreateRequest,

        @NotNull(message = "개인정보 활용 동의는 필수 값입니다.")
        Boolean personalDataCollection
) {

}
