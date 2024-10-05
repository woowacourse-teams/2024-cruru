package com.cruru.util.fixture;

import com.cruru.applicant.domain.dto.ApplicantCard;
import java.time.LocalDateTime;

public class ApplicantCardFixture {

    private ApplicantCardFixture() {

    }

    public static ApplicantCard evaluatedApplicantCard() {
        return new ApplicantCard(1L, "냥인", null, false, 5, 3.00, 1L);
    }

    public static ApplicantCard evaluatedApplicantCard(LocalDateTime createdAt) {
        return new ApplicantCard(1L, "냥인", createdAt, false, 5, 3.00, 1L);
    }

    public static ApplicantCard notEvaluatedApplicantCard() {
        return new ApplicantCard(2L, "렛서", null, false, 0, 0.00, 1L);
    }

    public static ApplicantCard notEvaluatedApplicantCard(LocalDateTime createdAt) {
        return new ApplicantCard(2L, "렛서", createdAt, false, 0, 0.00, 1L);
    }
}
