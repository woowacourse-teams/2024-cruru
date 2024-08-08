package com.cruru.evaluation.controller;

import com.cruru.evaluation.controller.dto.EvaluationCreateRequest;
import com.cruru.evaluation.controller.dto.EvaluationUpdateRequest;
import com.cruru.evaluation.controller.dto.EvaluationsResponse;
import com.cruru.evaluation.service.EvaluationService;
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

    private final EvaluationService evaluationService;

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody @Valid EvaluationCreateRequest request,
            @RequestParam(name = "processId") Long processId,
            @RequestParam(name = "applicantId") Long applicantId
    ) {
        evaluationService.create(request, processId, applicantId);
        String url = String.format("/v1/evaluations?processId=%d&applicantId=%d", processId, applicantId);
        return ResponseEntity.created(URI.create(url)).build();
    }

    @GetMapping
    public ResponseEntity<EvaluationsResponse> read(
            @RequestParam(name = "processId") Long processId,
            @RequestParam(name = "applicantId") Long applicantId
    ) {
        EvaluationsResponse response = evaluationService.read(processId, applicantId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{evaluationId}")
    public ResponseEntity<Void> update(
            @RequestBody EvaluationUpdateRequest request,
            @PathVariable long evaluationId
    ) {
        evaluationService.update(request, evaluationId);
        return ResponseEntity.ok().build();
    }
}
