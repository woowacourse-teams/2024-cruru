package com.cruru.applicant.controller.dto;

import com.cruru.question.controller.response.AnswerResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ApplicantAnswerResponses(
        @JsonProperty("details")
        List<AnswerResponse> answerResponses
) {

}
