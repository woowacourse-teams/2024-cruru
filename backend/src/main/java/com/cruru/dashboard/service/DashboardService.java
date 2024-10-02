package com.cruru.dashboard.service;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.club.domain.Club;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.DashboardApplyFormDto;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.dashboard.exception.DashboardNotFoundException;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.ProcessFactory;
import com.cruru.process.domain.repository.ProcessRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardRepository dashboardRepository;
    private final ProcessRepository processRepository;
    private final ApplicantRepository applicantRepository;
    private final ApplyFormRepository applyFormRepository;

    @Transactional
    public Dashboard create(Club club) {
        Dashboard savedDashboard = dashboardRepository.save(new Dashboard(club));

        List<Process> initProcesses = ProcessFactory.createInitProcesses(savedDashboard);
        processRepository.saveAll(initProcesses);

        return savedDashboard;
    }

    public Dashboard findById(Long id) {
        return dashboardRepository.findById(id)
                .orElseThrow(DashboardNotFoundException::new);
    }

    public List<DashboardApplyFormDto> findAllByClub(long clubId) {
        return applyFormRepository.findAllByClub(clubId);
    }

    public List<Applicant> findAllApplicants(Dashboard dashboard) {
        return applicantRepository.findAllByDashboard(dashboard);
    }
}
