package com.cruru.dashboard.service;

import static com.cruru.util.fixture.ClubFixture.createClub;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.club.exception.ClubNotFoundException;
import com.cruru.dashboard.controller.dto.DashboardCreateRequest;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("대시보드 서비스 테스트")
class DashboardServiceTest extends ServiceTest {

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

    @DisplayName("새로운 대시보드를 생성시, 기본 프로세스 2개가 생성된다.")
    @Test
    void create_createDefaultProcess() {
        // given
        Club club = createClub();
        clubRepository.save(club);
        DashboardCreateRequest request = new DashboardCreateRequest("크루루대시보드");

        // when
        long dashboardId = dashboardService.create(club.getId(), request);

        // then
        List<Process> processes = processRepository.findAllByDashboardId(dashboardId);
        assertThat(processes).hasSize(2);
    }

    @DisplayName("새로운 대시보드 생성 시, 존재하지 않는 동아리에 생성하려 하면 예외가 발생한다.")
    @Test
    void create_invalidClub() {
        // given
        DashboardCreateRequest request = new DashboardCreateRequest("크루루대시보드");
        long invalidClubId = -1;

        // when&then
        assertThatThrownBy(() -> dashboardService.create(invalidClubId, request))
                .isInstanceOf(ClubNotFoundException.class);
    }
}
