package com.cruru.applicant.controller;

import com.cruru.applicant.controller.request.EvaluationCreateRequest;
import com.cruru.applicant.controller.request.EvaluationUpdateRequest;
import com.cruru.applicant.controller.response.EvaluationResponses;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.Evaluation;
import com.cruru.applicant.facade.EvaluationFacade;
import com.cruru.auth.annotation.RequireAuthCheck;
import com.cruru.global.LoginProfile;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/evaluations")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationFacade evaluationFacade;

    @RequireAuthCheck(targetId = "applicantId", targetDomain = Applicant.class)
    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody @Valid EvaluationCreateRequest request,
            @RequestParam(name = "processId") Long processId,
            @RequestParam(name = "applicantId") Long applicantId,
            LoginProfile loginProfile
    ) {
        evaluationFacade.create(request, processId, applicantId);
        String url = String.format("/v1/evaluations?processId=%d&applicantId=%d", processId, applicantId);
        return ResponseEntity.created(URI.create(url)).build();
    }

    @RequireAuthCheck(targetId = "applicantId", targetDomain = Applicant.class)
    @GetMapping
    public ResponseEntity<EvaluationResponses> read(
            @RequestParam(name = "processId") Long processId,
            @RequestParam(name = "applicantId") Long applicantId,
            LoginProfile loginProfile
    ) {
        EvaluationResponses response = evaluationFacade.readEvaluationsOfApplicantInProcess(processId, applicantId);
        return ResponseEntity.ok(response);
    }

    @RequireAuthCheck(targetId = "evaluationId", targetDomain = Evaluation.class)
    @PatchMapping("/{evaluationId}")
    public ResponseEntity<Void> update(
            @RequestBody @Valid EvaluationUpdateRequest request,
            @PathVariable Long evaluationId,
            LoginProfile loginProfile
    ) {
        evaluationFacade.updateSingleEvaluation(request, evaluationId);
        return ResponseEntity.ok().build();
    }
}
