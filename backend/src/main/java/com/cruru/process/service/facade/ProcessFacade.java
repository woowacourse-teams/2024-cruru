package com.cruru.process.service.facade;

import com.cruru.applicant.controller.dto.ApplicantCardResponse;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.service.ApplyFormService;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.service.DashboardService;
import com.cruru.evaluation.service.EvaluationService;
import com.cruru.process.controller.dto.ProcessCreateRequest;
import com.cruru.process.controller.dto.ProcessResponse;
import com.cruru.process.controller.dto.ProcessResponses;
import com.cruru.process.controller.dto.ProcessUpdateRequest;
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

    public ProcessResponses readAllByDashboardId(long dashboardId) {
        Dashboard dashboard = dashboardService.findById(dashboardId);
        ApplyForm applyForm = applyFormService.findByDashboard(dashboard);
        List<Process> processes = processService.findAllByDashboard(dashboard);
        List<ProcessResponse> processResponses = toProcessResponses(processes);
        return new ProcessResponses(applyForm.getUrl(), processResponses, applyForm.getTitle());
    }

    private List<ProcessResponse> toProcessResponses(List<Process> processes) {
        return processes.stream()
                .map(this::toProcessResponse)
                .toList();
    }

    private ProcessResponse toProcessResponse(Process process) {
        List<Applicant> applicantsOfProcess = applicantService.findAllByProcess(process);
        List<ApplicantCardResponse> applicantCardResponses = toApplicantCardResponses(process, applicantsOfProcess);
        return new ProcessResponse(
                process.getId(),
                process.getSequence(),
                process.getName(),
                process.getDescription(),
                applicantCardResponses
        );
    }

    private List<ApplicantCardResponse> toApplicantCardResponses(
            Process process, List<Applicant> applicantsOfProcess
    ) {
        return applicantsOfProcess.stream()
                .map(applicant -> toApplicantCardResponse(process, applicant))
                .toList();
    }

    private ApplicantCardResponse toApplicantCardResponse(Process process, Applicant applicant) {
        int evaluationCount = evaluationService.count(process, applicant);
        return applicantService.toApplicantCardResponse(applicant, evaluationCount);
    }

    @Transactional
    public ProcessResponse update(ProcessUpdateRequest request, long processId) {
        Process process = processService.findById(processId);
        processService.update(request, process.getId());
        return toProcessResponse(process);
    }

    @Transactional
    public void delete(long processId) {
        processService.delete(processId);
    }
}
