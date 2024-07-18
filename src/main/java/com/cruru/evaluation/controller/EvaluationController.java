package com.cruru.evaluation.controller;

import com.cruru.evaluation.controller.dto.EvaluationCreateRequest;
import com.cruru.evaluation.service.EvaluationService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            @RequestParam(name = "process_id") Long processId,
            @RequestParam(name = "applicant_id") Long applicantId
    ) {
        long evaluationId = evaluationService.create(request, processId, applicantId);
        return ResponseEntity.created((URI.create("/v1/evaluations/" + evaluationId))).build();
    }
}
