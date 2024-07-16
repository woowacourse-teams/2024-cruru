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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProcessService {

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
