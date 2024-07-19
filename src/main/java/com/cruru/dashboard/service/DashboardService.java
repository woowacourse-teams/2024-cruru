package com.cruru.dashboard.service;

import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.club.exception.ClubNotFoundException;
import com.cruru.dashboard.controller.dto.DashboardCreateDto;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DashboardService {

    private static final Process DEFAULT_FIRST_PROCESS = new Process(1, "지원 접수", "지원자가 이력서를 제출하는 단계", null);
    private static final Process DEFAULT_LAST_PROCESS = new Process(2, "합격", "지원자가 최종적으로 합격한 단계", null);

    private final DashboardRepository dashboardRepository;
    private final ClubRepository clubRepository;
    private final ProcessRepository processRepository;

    @Transactional
    public long create(long clubId, DashboardCreateDto request) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);

        Dashboard savedDashboard = dashboardRepository.save(new Dashboard(request.name(), club));

        Process firstProcess = new Process(
                DEFAULT_FIRST_PROCESS.getSequence(),
                DEFAULT_FIRST_PROCESS.getName(),
                DEFAULT_FIRST_PROCESS.getDescription(),
                savedDashboard
        );

        Process lastProcess = new Process(
                DEFAULT_LAST_PROCESS.getSequence(),
                DEFAULT_LAST_PROCESS.getName(),
                DEFAULT_LAST_PROCESS.getDescription(),
                savedDashboard
        );

        processRepository.saveAll(List.of(firstProcess, lastProcess));

        return savedDashboard.getId();
    }
}
