package com.cruru.applicant.controller.request;

import com.cruru.applicant.domain.Applicant;
import com.cruru.auth.annotation.RequireAuth;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ApplicantMoveRequest(

        @RequireAuth(targetDomain = Applicant.class)
        @NotNull(message = "지원자 목록은 필수 값입니다.")
        List<Long> applicantIds
) {

}
