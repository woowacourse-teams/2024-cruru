package com.cruru.process.service;

import com.cruru.advice.InternalServerException;
import com.cruru.applicant.controller.dto.DashboardApplicantResponse;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.dashboard.exception.DashboardNotFoundException;
import com.cruru.evaluation.domain.repository.EvaluationRepository;
import com.cruru.process.controller.dto.ProcessCreateRequest;
import com.cruru.process.controller.dto.ProcessResponse;
import com.cruru.process.controller.dto.ProcessSimpleResponse;
import com.cruru.process.controller.dto.ProcessUpdateRequest;
import com.cruru.process.controller.dto.ProcessesResponse;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.process.exception.ProcessNotFoundException;
import com.cruru.process.exception.badrequest.ProcessCountException;
import com.cruru.process.exception.badrequest.ProcessDeleteEndsException;
import com.cruru.process.exception.badrequest.ProcessDeleteRemainingApplicantException;
import com.cruru.process.exception.badrequest.ProcessNoChangeException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProcessService {

    private static final int MAX_PROCESS_COUNT = 5;
    private static final int PROCESS_FIRST_SEQUENCE = 0;
    private static final int ZERO = 0;

    private final ApplicantRepository applicantRepository;
    private final ProcessRepository processRepository;

    public ProcessSimpleResponse toProcessSimpleResponse(Process process) {
        return new ProcessSimpleResponse(process.getId(), process.getName());
    }

    @Transactional
    public void create(ProcessCreateRequest request, Dashboard dashboard) {
        validateProcessCount(dashboard);
        List<Process> processes = findAllByDashboard(dashboard);

        rearrangeProcesses(request.sequence(), processes);

        processRepository.save(toProcess(request, dashboard));
    }

    private void validateProcessCount(Dashboard dashboard) {
        long size = processRepository.countByDashboard(dashboard);
        if (size >= MAX_PROCESS_COUNT) {
            throw new ProcessCountException(MAX_PROCESS_COUNT);
        }
    }

    private void rearrangeProcesses(int newProcessSequence, List<Process> processes) {
        processes.stream()
                .filter(process -> process.getSequence() >= newProcessSequence)
                .forEach(Process::increaseSequenceNumber);
    }

    public List<Process> findAllByDashboard(Dashboard dashboard) {
        return processRepository.findAllByDashboard(dashboard);
    }

    private Process toProcess(ProcessCreateRequest request, Dashboard dashboard) {
        return new Process(request.sequence(), request.name(), request.description(), dashboard);
    }

    public Process findFirstProcessOnDashboard(Dashboard dashboard) {
        List<Process> processes = findAllByDashboard(dashboard);
        return processes.stream()
                .filter(process -> process.getSequence() == PROCESS_FIRST_SEQUENCE)
                .findFirst()
                .orElseThrow(InternalServerException::new);
    }

    @Transactional
    public Process update(ProcessUpdateRequest request, long processId) {
        Process process = findById(processId);

        if (nothingToChange(request, process)) {
            throw new ProcessNoChangeException();
        }
        process.updateName(request.name());
        process.updateDescription(request.description());

        return process;
    }

    public Process findById(long processId) {
        return processRepository.findById(processId)
                .orElseThrow(ProcessNotFoundException::new);
    }

    private boolean nothingToChange(ProcessUpdateRequest request, Process process) {
        return request.name().equals(process.getName()) && request.description().equals(process.getDescription());
    }

    @Transactional
    public void delete(long processId) {
        Process process = findById(processId);
        validateFirstOrLastProcess(process);
        validateApplicantRemains(process);
        processRepository.deleteById(processId);
    }

    private void validateFirstOrLastProcess(Process process) {
        int processCount = (int) processRepository.countByDashboard(process.getDashboard());
        if (process.isSameSequence(PROCESS_FIRST_SEQUENCE) || process.isSameSequence(processCount - 1)) {
            throw new ProcessDeleteEndsException();
        }
    }

    private void validateApplicantRemains(Process process) {
        long applicantCount = applicantRepository.countByProcess(process);
        if (applicantCount > ZERO) {
            throw new ProcessDeleteRemainingApplicantException();
        }
    }
}
