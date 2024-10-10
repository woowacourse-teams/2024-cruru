package com.cruru.auth.controller;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.Evaluation;
import com.cruru.auth.annotation.RequireAuth;
import com.cruru.process.domain.Process;
import java.util.List;

public record AuthTestDto(

        @RequireAuth(targetDomain = Process.class)
        Long processId,

        @RequireAuth(targetDomain = Applicant.class)
        Long applicantId,

        @RequireAuth(targetDomain = Evaluation.class)
        List<Long> evaluationIds
) {

}
