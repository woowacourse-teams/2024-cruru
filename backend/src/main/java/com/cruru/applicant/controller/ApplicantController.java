package com.cruru.applicant.controller;

import com.cruru.applicant.controller.request.ApplicantMoveRequest;
import com.cruru.applicant.controller.request.ApplicantUpdateRequest;
import com.cruru.applicant.controller.request.ApplicantsRejectRequest;
import com.cruru.applicant.controller.response.ApplicantAnswerResponses;
import com.cruru.applicant.controller.response.ApplicantBasicResponse;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.facade.ApplicantFacade;
import com.cruru.auth.annotation.RequireAuth;
import com.cruru.auth.annotation.ValidAuth;
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

    @GetMapping("/{applicantId}")
    @ValidAuth
    public ResponseEntity<ApplicantBasicResponse> read(
            @RequireAuth(targetDomain = Applicant.class) @PathVariable Long applicantId,
            LoginProfile loginProfile
    ) {
        ApplicantBasicResponse applicantResponse = applicantFacade.readBasicById(applicantId);
        return ResponseEntity.ok().body(applicantResponse);
    }

    @GetMapping("/{applicantId}/detail")
    @ValidAuth
    public ResponseEntity<ApplicantAnswerResponses> readDetail(
            @RequireAuth(targetDomain = Applicant.class) @PathVariable Long applicantId,
            LoginProfile loginProfile
    ) {
        ApplicantAnswerResponses applicantAnswerResponses = applicantFacade.readDetailById(applicantId);
        return ResponseEntity.ok().body(applicantAnswerResponses);
    }

    @PatchMapping("/{applicantId}")
    @ValidAuth
    public ResponseEntity<Void> updateInformation(
            @RequireAuth(targetDomain = Applicant.class) @PathVariable Long applicantId,
            @RequestBody @Valid ApplicantUpdateRequest request,
            LoginProfile loginProfile
    ) {
        applicantFacade.updateApplicantInformation(applicantId, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/move-process/{processId}")
    @ValidAuth
    public ResponseEntity<Void> updateProcess(
            @RequireAuth(targetDomain = Process.class) @PathVariable Long processId,
            @RequestBody @Valid ApplicantMoveRequest moveRequest,
            LoginProfile loginProfile
    ) {
        applicantFacade.updateApplicantProcess(processId, moveRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{applicantId}/reject")
    @ValidAuth
    public ResponseEntity<Void> reject(
            @RequireAuth(targetDomain = Applicant.class) @PathVariable Long applicantId,
            LoginProfile loginProfile
    ) {
        applicantFacade.reject(applicantId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{applicantId}/unreject")
    @ValidAuth
    public ResponseEntity<Void> unreject(
            @RequireAuth(targetDomain = Applicant.class) @PathVariable Long applicantId,
            LoginProfile loginProfile
    ) {
        applicantFacade.unreject(applicantId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/reject")
    @ValidAuth
    public ResponseEntity<Void> reject(@RequestBody @Valid ApplicantsRejectRequest request, LoginProfile loginProfile) {
        applicantFacade.reject(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/unreject")
    @ValidAuth
    public ResponseEntity<Void> unreject(
            @RequestBody @Valid ApplicantsRejectRequest request, LoginProfile loginProfile) {
        applicantFacade.unreject(request);
        return ResponseEntity.ok().build();
    }
}
