package com.cruru.process.service.facade;

import com.cruru.applicant.controller.dto.ApplicantBasicResponse;
import com.cruru.applicant.controller.dto.ApplicantResponse;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.process.controller.dto.ProcessSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicantFacade {

    private final ApplicantService applicantService;

    public ApplicantBasicResponse findById(long applicantId) {
        Applicant applicant = applicantService.findById(applicantId);
        return toApplicantBasicResponse(applicant);
    }

    private ApplicantBasicResponse toApplicantBasicResponse(Applicant applicant) {
        return new ApplicantBasicResponse(
                new ApplicantResponse(
                        applicant.getId(),
                        applicant.getName(),
                        applicant.getEmail(),
                        applicant.getPhone(),
                        applicant.getCreatedDate()
                ),
                new ProcessSimpleResponse(
                        applicant.getProcess().getId(),
                        applicant.getProcess().getName()
                )
        );
    }
}
