package com.cruru.applicant.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ApplicantMoveRequest(
        @JsonProperty(value = "applicant_ids")
        List<Long> applicantIds
) {

}
