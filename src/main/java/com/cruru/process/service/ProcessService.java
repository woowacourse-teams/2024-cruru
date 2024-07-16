package com.cruru.process.service;

import com.cruru.applicant.controller.dto.DashboardApplicantDto;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.dashboard.exception.DashboardNotFoundException;
import com.cruru.process.controller.dto.ProcessCreateRequest;
import com.cruru.process.controller.dto.ProcessResponse;
import com.cruru.process.controller.dto.ProcessesResponse;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.process.exception.ProcessBadRequestException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProcessService {

    public static final int MAX_PROCESS_COUNT = 5;
    private final ApplicantRepository applicantRepository;
    private final ProcessRepository processRepository;
    private final DashboardRepository dashboardRepository;

    public ProcessesResponse findByDashboardId(Long dashboardId) {
        dashboardRepository.findById(dashboardId)
                .orElseThrow(DashboardNotFoundException::new);

        return new ProcessesResponse(processRepository.findAllByDashboardId(dashboardId)
                .stream()
                .map(this::toProcessResponse)
                .toList());
    }

    private ProcessResponse toProcessResponse(Process process) {
        List<DashboardApplicantDto> dashboardApplicantDtos = applicantRepository.findAllByProcess(process)
                .stream()
                .map(this::toApplicantDto)
                .toList();

        return new ProcessResponse(
                process.getId(),
                process.getSequence(),
                process.getName(),
                process.getDescription(),
                dashboardApplicantDtos
        );
    }

    private DashboardApplicantDto toApplicantDto(Applicant applicant) {
        return new DashboardApplicantDto(applicant.getId(), applicant.getName(), applicant.getCreatedDate());
    }

    @Transactional
    public void create(long dashboardId, ProcessCreateRequest request) {
        List<Process> allByDashboardId = processRepository.findAllByDashboardId(dashboardId);

        if (allByDashboardId.size() == MAX_PROCESS_COUNT) {
            throw new ProcessBadRequestException();
        }

        Process priorProcess = processRepository.findById(request.priorProcessId()).orElseThrow();

        allByDashboardId.stream()
                .filter(process -> process.getSequence() > priorProcess.getSequence())
                .forEach(Process::increaseSequenceNumber);

        processRepository.save(
                new Process(priorProcess.getSequence() + 1,
                        request.name(),
                        request.description(),
                        dashboardRepository.findById(dashboardId).orElseThrow())
        );
    }

    @Transactional
    public void delete(long processId) {
        processRepository.deleteById(processId);
    }
}
