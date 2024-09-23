package com.cruru.applicant.controller;

import com.cruru.applicant.controller.request.ApplicantMoveRequest;
import com.cruru.applicant.controller.request.ApplicantUpdateRequest;
import com.cruru.applicant.controller.response.ApplicantAnswerResponses;
import com.cruru.applicant.controller.response.ApplicantBasicResponse;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.facade.ApplicantFacade;
import com.cruru.auth.annotation.RequireAuthCheck;
import com.cruru.global.LoginProfile;
import com.cruru.process.domain.Process;
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

    @RequireAuthCheck(targetId = "applicantId", targetDomain = Applicant.class)
    @GetMapping("/{applicantId}")
    public ResponseEntity<ApplicantBasicResponse> read(@PathVariable Long applicantId, LoginProfile loginProfile) {
        ApplicantBasicResponse applicantResponse = applicantFacade.readBasicById(applicantId);
        return ResponseEntity.ok().body(applicantResponse);
    }

    @RequireAuthCheck(targetId = "applicantId", targetDomain = Applicant.class)
    @GetMapping("/{applicantId}/detail")
    public ResponseEntity<ApplicantAnswerResponses> readDetail(
            @PathVariable Long applicantId,
            LoginProfile loginProfile
    ) {
        ApplicantAnswerResponses applicantAnswerResponses = applicantFacade.readDetailById(applicantId);
        return ResponseEntity.ok().body(applicantAnswerResponses);
    }

    @RequireAuthCheck(targetId = "applicantId", targetDomain = Applicant.class)
    @PatchMapping("/{applicantId}")
    public ResponseEntity<Void> updateInformation(
            @PathVariable Long applicantId,
            @RequestBody @Valid ApplicantUpdateRequest request,
            LoginProfile loginProfile
    ) {
        applicantFacade.updateApplicantInformation(applicantId, request);
        return ResponseEntity.ok().build();
    }

    @RequireAuthCheck(targetId = "processId", targetDomain = Process.class)
    @PutMapping("/move-process/{processId}")
    public ResponseEntity<Void> updateProcess(
            @PathVariable Long processId,
            @RequestBody @Valid ApplicantMoveRequest moveRequest,
            LoginProfile loginProfile
    ) {
        applicantFacade.updateApplicantProcess(processId, moveRequest);
        return ResponseEntity.ok().build();
    }

    @RequireAuthCheck(targetId = "applicantId", targetDomain = Applicant.class)
    @PatchMapping("/{applicantId}/reject")
    public ResponseEntity<Void> reject(@PathVariable Long applicantId, LoginProfile loginProfile) {
        applicantFacade.reject(applicantId);
        return ResponseEntity.ok().build();
    }

    @RequireAuthCheck(targetId = "applicantId", targetDomain = Applicant.class)
    @PatchMapping("/{applicantId}/unreject")
    public ResponseEntity<Void> unreject(@PathVariable Long applicantId, LoginProfile loginProfile) {
        applicantFacade.unreject(applicantId);
        return ResponseEntity.ok().build();
    }
}
