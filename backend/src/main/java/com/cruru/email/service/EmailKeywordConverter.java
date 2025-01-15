package com.cruru.email.service;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.service.ApplyFormService;
import com.cruru.club.domain.Club;
import com.cruru.email.domain.EmailKeyword;
import java.util.EnumMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailKeywordConverter {

    private final ApplyFormService applyFormService;
    private final ApplicantService applicantService;

    public String convert(String content, Club club, Applicant applicant) {
        Map<EmailKeyword, String> matcher = new EnumMap<>(EmailKeyword.class);

        applicant = applicantService.findById(applicant.getId());
        ApplyForm applyForm = applyFormService.findByDashboard(applicant.getDashboard());

        matcher.put(EmailKeyword.APPLICANT_NAME, applicant.getName());
        matcher.put(EmailKeyword.CLUB_NAME, club.getName());
        matcher.put(EmailKeyword.APPLY_FORM_TITLE, applyForm.getTitle());
        matcher.put(EmailKeyword.PROCESS_NAME, applicant.getProcess().getName());

        return EmailKeyword.convert(content, matcher);
    }
}

