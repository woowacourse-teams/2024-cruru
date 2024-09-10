package com.cruru.applicant.controller;

import com.cruru.applicant.controller.dto.EvaluationCreateRequest;
import com.cruru.applicant.controller.dto.EvaluationResponses;
import com.cruru.applicant.controller.dto.EvaluationUpdateRequest;
import com.cruru.applicant.service.facade.EvaluationFacade;
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

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody @Valid EvaluationCreateRequest request,
            @RequestParam(name = "processId") Long processId,
            @RequestParam(name = "applicantId") Long applicantId
    ) {
        evaluationFacade.create(request, processId, applicantId);
        String url = String.format("/v1/evaluations?processId=%d&applicantId=%d", processId, applicantId);
        return ResponseEntity.created(URI.create(url)).build();
    }

    @GetMapping
    public ResponseEntity<EvaluationResponses> read(
            @RequestParam(name = "processId") Long processId,
            @RequestParam(name = "applicantId") Long applicantId
    ) {
        EvaluationResponses response = evaluationFacade.readEvaluationsOfApplicantInProcess(processId, applicantId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{evaluationId}")
    public ResponseEntity<Void> update(
            @RequestBody @Valid EvaluationUpdateRequest request,
            @PathVariable long evaluationId
    ) {
        evaluationFacade.updateSingleEvaluation(request, evaluationId);
        return ResponseEntity.ok().build();
    }
}
