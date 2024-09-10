package com.cruru.applicant.controller.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ApplicantMoveRequest(
        @NotNull(message = "지원자 목록은 필수 값입니다.")
        List<Long> applicantIds
) {

}
