package com.cruru.dashboard.service;

import static com.cruru.util.fixture.ClubFixture.createClub;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.club.exception.ClubNotFoundException;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.DashboardFixture;
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
        Club club = createClub();
        clubRepository.save(club);

        // when
        Dashboard createdDashboard = dashboardService.create(club.getId());

        // then
        List<Process> processes = processRepository.findAllByDashboardId(createdDashboard.getId());
        assertThat(processes).hasSize(2);
    }

    @DisplayName("새로운 대시보드 생성 시, 존재하지 않는 동아리에 생성하려 하면 예외가 발생한다.")
    @Test
    void create_invalidClub() {
        // given
        long invalidClubId = -1L;

        // when&then
        assertThatThrownBy(() -> dashboardService.create(invalidClubId))
                .isInstanceOf(ClubNotFoundException.class);
    }


    @DisplayName("대시보드를 ID를 통해 조회한다.")
    @Test
    void findById() {
        // given
        Dashboard backendDashboard = dashboardRepository.save(DashboardFixture.createBackendDashboard());

        // when&then
        long id = backendDashboard.getId();
        assertDoesNotThrow(() -> dashboardService.findById(id));
        assertThat(dashboardService.findById(backendDashboard.getId())).isEqualTo(backendDashboard);
    }

    @DisplayName("동아리 ID로 동아리가 가지고 있는 모든 대시보드 ID를 조회한다.")
    @Test
    void findAllByClubId() {
        // given
        Club club = clubRepository.save(createClub());
        Dashboard backendDashboard = dashboardRepository.save(DashboardFixture.createBackendDashboard(club));
        Dashboard frontendDashboard = dashboardRepository.save(DashboardFixture.createFrontendDashboard(club));

        // when & then
        long clubId = club.getId();
        assertThat(dashboardService.findAllByClubId(clubId)).containsExactlyInAnyOrder(
                backendDashboard,
                frontendDashboard
        );
    }
}
