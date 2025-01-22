package com.cruru.applicant.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ApplicantCsvLine(
        String name,
        String email,
        String phone,
        LocalDateTime submissionDate,
        List<String> answers
) {

}
