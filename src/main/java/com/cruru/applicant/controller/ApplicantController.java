package com.cruru.applicant.controller;

import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.controller.dto.ApplicantResponse;
import com.cruru.applicant.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/applicants")
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;

    @PutMapping("/move-process/{processId}")
    public ResponseEntity<Void> updateApplicantProcess(
            @PathVariable long processId,
            @RequestBody ApplicantMoveRequest moveRequest) {

        applicantService.updateApplicantProcess(processId, moveRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{applicant_id}")
    public ResponseEntity<ApplicantResponse> read(@PathVariable("applicant_id") Long id) {
        ApplicantResponse applicantResponse = applicantService.findById(id);
        return ResponseEntity.ok().body(applicantResponse);
    }
}
