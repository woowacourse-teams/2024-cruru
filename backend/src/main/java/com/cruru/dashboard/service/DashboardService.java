package com.cruru.dashboard.service;

import com.cruru.club.domain.Club;
import com.cruru.dashboard.domain.Dashboard;
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

    @Transactional
    public Dashboard create(Club club) {
        Dashboard savedDashboard = dashboardRepository.save(new Dashboard(club));

        List<Process> initProcesses = ProcessFactory.createInitProcesses(savedDashboard);
        processRepository.saveAll(initProcesses);

        return savedDashboard;
    }

    public Dashboard findById(long id) {
        return dashboardRepository.findById(id)
                .orElseThrow(DashboardNotFoundException::new);
    }

    public List<Dashboard> findAllByClub(Club club) {
        return dashboardRepository.findAllByClub(club);
    }
}
