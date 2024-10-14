package com.cruru.process.facade;

import com.cruru.applicant.controller.response.ApplicantCardResponse;
import com.cruru.applicant.domain.dto.ApplicantCard;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.applicant.service.EvaluationService;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.service.ApplyFormService;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.service.DashboardService;
import com.cruru.process.controller.request.ProcessCreateRequest;
import com.cruru.process.controller.request.ProcessUpdateRequest;
import com.cruru.process.controller.response.ProcessResponse;
import com.cruru.process.controller.response.ProcessResponses;
import com.cruru.process.domain.Process;
import com.cruru.process.service.ProcessService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProcessFacade {

    private final ProcessService processService;
    private final DashboardService dashboardService;
    private final ApplicantService applicantService;
    private final EvaluationService evaluationService;
    private final ApplyFormService applyFormService;

    @Transactional
    public void create(ProcessCreateRequest request, long dashboardId) {
        Dashboard dashboard = dashboardService.findById(dashboardId);
        processService.create(request, dashboard);
    }

    public ProcessResponses readAllByDashboardId(
            long dashboardId,
            Double minScore,
            Double maxScore,
            String evaluationStatus,
            String sortByCreatedAt,
            String sortByScore
    ) {
        ApplyForm applyForm = applyFormService.findByDashboardId(dashboardId);
        List<Process> processes = processService.findAllByDashboard(dashboardId);

        List<ApplicantCard> applicantCards = applicantService.findApplicantCards(
                processes, minScore, maxScore, evaluationStatus, sortByCreatedAt, sortByScore);

        List<ProcessResponse> processResponses = processes.stream()
                .map(process -> toProcessResponse(process, applicantCards))
                .toList();

        return new ProcessResponses(
                applyForm.getId(),
                processResponses,
                applyForm.getTitle(),
                applyForm.getStartDate(),
                applyForm.getEndDate()
        );
    }

    private ProcessResponse toProcessResponse(Process process, List<ApplicantCard> applicantCards) {
        List<ApplicantCardResponse> applicantCardResponses = applicantCards.stream()
                .filter(card -> card.processId() == process.getId())
                .map(ApplicantCard::toResponse)
                .toList();

        return new ProcessResponse(
                process.getId(),
                process.getSequence(),
                process.getName(),
                process.getDescription(),
                applicantCardResponses
        );
    }

    @Transactional
    public ProcessResponse update(ProcessUpdateRequest request, long processId) {
        Process process = processService.findById(processId);

        processService.update(request, process.getId());
        return toProcessResponse(process);
    }

    private ProcessResponse toProcessResponse(Process process) {
        List<ApplicantCardResponse> applicantCardResponses = applicantService.findApplicantCards(process)
                .stream()
                .map(ApplicantCard::toResponse)
                .toList();

        return new ProcessResponse(
                process.getId(),
                process.getSequence(),
                process.getName(),
                process.getDescription(),
                applicantCardResponses
        );
    }

    @Transactional
    public void delete(long processId) {
        evaluationService.deleteByProcess(processId);
        processService.delete(processId);
    }
}
