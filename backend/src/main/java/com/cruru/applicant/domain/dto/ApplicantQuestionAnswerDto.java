package com.cruru.applicant.domain.dto;

import java.time.LocalDateTime;

public record ApplicantQuestionAnswerDto(
        Long applicantId,
        String applicantName,
        String email,
        String phone,
        LocalDateTime submissionDate,

        Long questionId,
        Integer questionSequence,
        String questionContent,

        String answerContent
) {

}
