package com.cruru.dashboard.service;

import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.club.exception.ClubNotFoundException;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
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
    private final ClubRepository clubRepository;
    private final ProcessRepository processRepository;

    @Transactional
    public Dashboard create(long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);
        Dashboard savedDashboard = dashboardRepository.save(new Dashboard(club));

        Process firstProcess = ProcessFactory.createFirstOf(savedDashboard);
        Process lastProcess = ProcessFactory.createLastOf(savedDashboard);

        processRepository.saveAll(List.of(firstProcess, lastProcess));

        return savedDashboard;
    }

    @Transactional
    public List<Dashboard> findAllByClubId(long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);
        return dashboardRepository.findAllByClubId(club.getId());
    }
}
