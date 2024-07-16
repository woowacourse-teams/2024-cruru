package com.cruru.process.controller.dto;

import com.cruru.applicant.controller.dto.DashboardApplicantDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ProcessResponse(
        @JsonProperty("process_id")
        Long processId,

        @JsonProperty("order_index")
        Integer sequence,

        String name,
        String description,

        @JsonProperty("applicants")
        List<DashboardApplicantDto> dashboardApplicantDtos
) {

}
