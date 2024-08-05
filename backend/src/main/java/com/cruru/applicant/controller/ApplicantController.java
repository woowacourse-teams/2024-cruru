package com.cruru.applicant.controller;

import com.cruru.applicant.controller.dto.ApplicantBasicResponse;
import com.cruru.applicant.controller.dto.ApplicantDetailResponse;
import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.service.ApplicantService;
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

    @PutMapping("/move-process/{processId}")
    public ResponseEntity<Void> updateApplicantProcess(
            @PathVariable Long processId,
            @RequestBody @Valid ApplicantMoveRequest moveRequest
    ) {
        applicantService.updateApplicantProcess(processId, moveRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{applicantId}")
    public ResponseEntity<ApplicantBasicResponse> read(@PathVariable("applicantId") Long applicantId) {
        ApplicantBasicResponse applicantResponse = applicantService.findById(applicantId);
        return ResponseEntity.ok().body(applicantResponse);
    }

    @GetMapping("/{applicantId}/detail")
    public ResponseEntity<ApplicantDetailResponse> readDetail(@PathVariable("applicantId") Long applicantId) {
        ApplicantDetailResponse applicantDetailResponse = applicantService.findDetailById(applicantId);
        return ResponseEntity.ok().body(applicantDetailResponse);
    }

    @PatchMapping("/{applicantId}/reject")
    public ResponseEntity<ApplicantDetailResponse> reject(@PathVariable("applicantId") Long applicantId) {
        applicantService.reject(applicantId);
        return ResponseEntity.ok().build();
    }
}
