package com.cruru.dashboard.service.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.question.controller.dto.ChoiceCreateRequest;
import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.dashboard.controller.dto.DashboardCreateRequest;
import com.cruru.dashboard.controller.dto.DashboardCreateResponse;
import com.cruru.dashboard.controller.dto.DashboardPreviewResponse;
import com.cruru.dashboard.controller.dto.DashboardsOfClubResponse;
import com.cruru.dashboard.controller.dto.StatsResponse;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.controller.dto.QuestionCreateRequest;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.ClubFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.LocalDateFixture;
import com.cruru.util.fixture.ProcessFixture;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("대시보드 파사드 서비스 테스트")
class DashboardFacadeTest extends ServiceTest {

    @Autowired
    private DashboardFacade dashboardFacade;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    private Club club;

    @BeforeEach
    void setUp() {
        club = clubRepository.save(ClubFixture.create(defaultMember));
    }

    @DisplayName("대시보드(공고)를 생성한다.")
    @Test
    void create() {
        // given
        List<ChoiceCreateRequest> choiceCreateRequests = List.of(new ChoiceCreateRequest("선택지1", 1));
        List<QuestionCreateRequest> questionCreateRequests = List.of(
                new QuestionCreateRequest("DROPDOWN", "객관식질문1", choiceCreateRequests, 1, false));
        String title = "크루루대시보드";
        String postingContent = "# 공고 내용";
        LocalDateTime startDate = LocalDateFixture.oneDayLater();
        LocalDateTime endDate = LocalDateFixture.oneWeekLater();
        DashboardCreateRequest request = new DashboardCreateRequest(
                title,
                postingContent,
                questionCreateRequests,
                startDate,
                endDate
        );

        // when
        DashboardCreateResponse response = dashboardFacade.create(loginProfile, club.getId(), request);

        // then
        assertThat(dashboardRepository.findById(response.dashboardId())).isPresent();
    }

    @DisplayName("다건의 대시보드 정보를 조회한다.")
    @Test
    void findAllDashboardsByClubId() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend(club));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        Process firstProcess = processRepository.save(ProcessFixture.applyType(dashboard));
        Process lastProcess = processRepository.save(ProcessFixture.approveType(dashboard));

        List<Applicant> applicants = List.of(
                // 마지막 프로세스에 있으면서 불합격 상태인 경우, 불합격
                ApplicantFixture.rejectedRush(lastProcess),
                ApplicantFixture.rejectedRush(firstProcess),
                ApplicantFixture.pendingDobby(lastProcess),
                ApplicantFixture.pendingDobby(firstProcess),
                ApplicantFixture.pendingDobby(firstProcess),
                ApplicantFixture.pendingDobby(firstProcess)
        );
        applicantRepository.saveAll(applicants);

        // when
        DashboardsOfClubResponse dashboardsOfClubResponse =
                dashboardFacade.findAllDashboardsByClubId(loginProfile, club.getId());

        // then
        DashboardPreviewResponse dashboardPreview = dashboardsOfClubResponse.dashboardPreviewResponses().get(0);
        StatsResponse stats = dashboardPreview.stats();
        assertAll(
                () -> assertThat(dashboardsOfClubResponse.clubName()).isEqualTo(club.getName()),
                () -> assertThat(dashboardPreview.dashboardId()).isEqualTo(dashboard.getId()),
                () -> assertThat(dashboardPreview.title()).isEqualTo(applyForm.getTitle()),
                () -> assertThat(dashboardPreview.applyFormId()).isEqualTo(applyForm.getId()),
                () -> assertThat(dashboardPreview.endDate()).isEqualTo(applyForm.getEndDate()),
                () -> assertThat(stats.accept()).isEqualTo(1),
                () -> assertThat(stats.fail()).isEqualTo(2),
                () -> assertThat(stats.inProgress()).isEqualTo(3)
        );
    }
}
