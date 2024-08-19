package com.cruru.dashboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ClubFixture;
import com.cruru.util.fixture.DashboardFixture;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("대시보드 서비스 테스트")
class DashboardServiceTest extends ServiceTest {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @DisplayName("새로운 대시보드를 생성시, 기본 프로세스 2개가 생성된다.")
    @Test
    void create_createDefaultProcess() {
        // given
        Club club = ClubFixture.create();
        clubRepository.save(club);

        // when
        Dashboard createdDashboard = dashboardService.create(club);

        // then
        List<Process> processes = processRepository.findAllByDashboardId(createdDashboard.getId())
                .stream()
                .sorted(Comparator.comparingInt(Process::getSequence))
                .toList();
        assertAll(
                () -> assertThat(processes).hasSize(2),
                () -> assertThat(processes.get(0).getSequence()).isEqualTo(0),
                () -> assertThat(processes.get(1).getSequence()).isEqualTo(1)
        );
    }

    @DisplayName("대시보드를 ID를 통해 조회한다.")
    @Test
    void findById() {
        // given
        Dashboard backendDashboard = dashboardRepository.save(DashboardFixture.backend());

        // when&then
        long id = backendDashboard.getId();
        assertDoesNotThrow(() -> dashboardService.findById(id));
        assertThat(dashboardService.findById(backendDashboard.getId())).isEqualTo(backendDashboard);
    }

    @DisplayName("동아리 ID로 동아리가 가지고 있는 모든 대시보드 ID를 조회한다.")
    @Test
    void findAllByClub() {
        // given
        Club club = clubRepository.save(ClubFixture.create());
        Dashboard backendDashboard = dashboardRepository.save(DashboardFixture.backend(club));
        Dashboard frontendDashboard = dashboardRepository.save(DashboardFixture.frontend(club));

        // when & then
        assertThat(dashboardService.findAllByClub(club)).containsExactlyInAnyOrder(
                backendDashboard,
                frontendDashboard
        );
    }
}
