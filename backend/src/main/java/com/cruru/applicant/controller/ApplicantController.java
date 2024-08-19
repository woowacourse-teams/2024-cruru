package com.cruru.applicant.controller;

import com.cruru.applicant.controller.dto.ApplicantAnswerResponses;
import com.cruru.applicant.controller.dto.ApplicantBasicResponse;
import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.controller.dto.ApplicantUpdateRequest;
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

    private final ApplicantFacade applicantFacade;

    @GetMapping("/{applicantId}")
    public ResponseEntity<ApplicantBasicResponse> read(@PathVariable Long applicantId) {
        ApplicantBasicResponse applicantResponse = applicantFacade.readBasicById(applicantId);
        return ResponseEntity.ok().body(applicantResponse);
    }

    @GetMapping("/{applicantId}/detail")
    public ResponseEntity<ApplicantAnswerResponses> readDetail(@PathVariable Long applicantId) {
        ApplicantAnswerResponses applicantAnswerResponses = applicantFacade.readDetailById(applicantId);
        return ResponseEntity.ok().body(applicantAnswerResponses);
    }

    @PatchMapping("/{applicantId}")
    public ResponseEntity<Void> updateInformation(
            @PathVariable Long applicantId,
            @RequestBody @Valid ApplicantUpdateRequest request
    ) {
        applicantFacade.updateApplicantInformation(applicantId, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/move-process/{processId}")
    public ResponseEntity<Void> updateProcess(
            @PathVariable Long processId,
            @RequestBody @Valid ApplicantMoveRequest moveRequest
    ) {
        applicantFacade.updateApplicantProcess(processId, moveRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{applicantId}/reject")
    public ResponseEntity<ApplicantAnswerResponses> reject(@PathVariable Long applicantId) {
        applicantFacade.reject(applicantId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{applicantId}/unreject")
    public ResponseEntity<ApplicantAnswerResponses> unreject(@PathVariable Long applicantId) {
        applicantFacade.unreject(applicantId);
        return ResponseEntity.ok().build();
    }
}
