package com.cruru.applicant.domain.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ApplicantRowDto(
        String name,
        String email,
        String phone,
        LocalDateTime submissionDate,
        Map<String, String> questionAnswers
) {

}
