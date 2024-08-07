package com.cruru.applicant.controller;

import com.cruru.applicant.controller.dto.ApplicantBasicResponse;
import com.cruru.applicant.controller.dto.ApplicantDetailResponse;
import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.controller.dto.ApplicantUpdateRequest;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.applicant.service.facade.ApplicantFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/applicants")
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;
    private final ApplicantFacade applicantFacade;

    @PutMapping("/move-process/{processId}")
    public ResponseEntity<Void> updateApplicantProcess(
            @PathVariable Long processId,
            @RequestBody @Valid ApplicantMoveRequest moveRequest
    ) {
        applicantFacade.updateApplicantProcess(processId, moveRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{applicantId}")
    public ResponseEntity<ApplicantBasicResponse> read(@PathVariable("applicantId") Long applicantId) {
        ApplicantBasicResponse applicantResponse = applicantFacade.readBasicById(applicantId);
        return ResponseEntity.ok().body(applicantResponse);
    }

    @GetMapping("/{applicantId}/detail")
    public ResponseEntity<ApplicantDetailResponse> readDetail(@PathVariable("applicantId") Long applicantId) {
        ApplicantDetailResponse applicantDetailResponse = applicantFacade.readDetailById(applicantId);
        return ResponseEntity.ok().body(applicantDetailResponse);
    }

    @PatchMapping("/{applicantId}/reject")
    public ResponseEntity<ApplicantDetailResponse> reject(@PathVariable("applicantId") Long applicantId) {
        applicantFacade.updateApplicantToReject(applicantId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{applicantId}")
    public ResponseEntity<Void> update(
            @PathVariable("applicantId") Long applicantId,
            @RequestBody @Valid ApplicantUpdateRequest request
    ) {
        applicantFacade.updateApplicantInformation(request, applicantId);
        return ResponseEntity.ok().build();
    }
}
