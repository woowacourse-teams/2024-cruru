package com.cruru.dashboard.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.dashboard.controller.dto.DashboardCreateDto;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("대시보드 서비스 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class DashboardServiceTest {

    @Autowired
    DashboardService dashboardService;

    @Autowired
    DashboardRepository dashboardRepository;

    @Autowired
    ProcessRepository processRepository;

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    ApplicantRepository applicantRepository;

    @BeforeEach
    void setUp() {
        applicantRepository.deleteAll();
        processRepository.deleteAll();
        dashboardRepository.deleteAll();
        clubRepository.deleteAll();
    }

    @DisplayName("새로운 대시보드를 생성시, 기본 프로세스 2개가 생성된다.")
    @Test
    void createDefaultProcessWhenCreateDashBoard() {
        // given
        Club club = new Club("크루루동아리", null);
        clubRepository.save(club);

        // when
        DashboardCreateDto request = new DashboardCreateDto("크루루대시보드");
        Long dashboardId = dashboardService.create(club.getId(), request);

        // then
        List<Process> processes = processRepository.findAllByDashboardId(dashboardId);
        assertThat(processes).hasSize(2);
    }
}
