package com.cruru.applicant.facade;

import com.cruru.applicant.controller.request.EvaluationCreateRequest;
import com.cruru.applicant.controller.request.EvaluationUpdateRequest;
import com.cruru.applicant.controller.response.EvaluationResponse;
import com.cruru.applicant.controller.response.EvaluationResponses;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.Evaluation;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.applicant.service.EvaluationService;
import com.cruru.process.domain.Process;
import com.cruru.process.service.ProcessService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EvaluationFacade {

    private final EvaluationService evaluationService;
    private final ApplicantService applicantService;
    private final ProcessService processService;

    @Transactional
    public void create(EvaluationCreateRequest request, long processId, long applicantId) {
        Process process = processService.findByIdFetchingMember(processId);
        Applicant applicant = applicantService.findByIdFetchingMember(applicantId);

        evaluationService.create(request, process, applicant);
    }

    public EvaluationResponses readEvaluationsOfApplicantInProcess(long processId, long applicantId) {
        Process process = processService.findByIdFetchingMember(processId);
        Applicant applicant = applicantService.findByIdFetchingMember(applicantId);

        return toEvaluationResponses(evaluationService.findAllByProcessAndApplicant(process, applicant));
    }

    private EvaluationResponses toEvaluationResponses(List<Evaluation> evaluations) {
        List<EvaluationResponse> responses = evaluations.stream()
                .map(this::toEvaluationResponse)
                .toList();

        return new EvaluationResponses(responses);
    }

    private EvaluationResponse toEvaluationResponse(Evaluation evaluation) {
        return new EvaluationResponse(
                evaluation.getId(),
                evaluation.getScore(),
                evaluation.getContent(),
                evaluation.getCreatedDate()
        );
    }

    @Transactional
    public void updateSingleEvaluation(EvaluationUpdateRequest request, Long evaluationId) {
        Evaluation evaluation = evaluationService.findByIdFetchingMember(evaluationId);
        evaluationService.update(request, evaluation);
    }
}
