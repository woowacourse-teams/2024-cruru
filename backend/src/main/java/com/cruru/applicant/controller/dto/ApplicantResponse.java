package com.cruru.applicant.controller.dto;

import java.time.LocalDateTime;

public record ApplicantResponse(
        long id,

        String name,

        String email,

        String phone,

        boolean isRejected,

        LocalDateTime createdAt
) {

}
