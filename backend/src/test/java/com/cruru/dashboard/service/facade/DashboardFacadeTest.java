package com.cruru.dashboard.service.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.choice.controller.dto.ChoiceCreateRequest;
import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.dashboard.controller.dto.ApplyFormUrlResponse;
import com.cruru.dashboard.controller.dto.DashboardCreateRequest;
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
        club = clubRepository.save(ClubFixture.create());
    }

    @DisplayName("대시보드(공고)를 생성한다.")
    @Test
    void create() {
        // given
        List<ChoiceCreateRequest> choiceCreateRequests = List.of(new ChoiceCreateRequest("선택지1", 1));
        List<QuestionCreateRequest> questionCreateRequests = List.of(
                new QuestionCreateRequest("DROPDOWN", "객관식질문1", "하나를 선택한다.", choiceCreateRequests, 1, false));
        String title = "크루루대시보드";
        String postingContent = "# 공고 내용";
        LocalDateTime startDate = LocalDateTime.of(2000, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2999, 12, 31, 23, 59);
        DashboardCreateRequest request = new DashboardCreateRequest(
                title,
                postingContent,
                questionCreateRequests,
                startDate,
                endDate
        );

        // when
        long savedDashboardId = dashboardFacade.create(club.getId(), request);

        // then
        assertThat(dashboardRepository.findById(savedDashboardId)).isPresent();
    }

    @DisplayName("대시보드로 공고 URL을 찾는다.")
    @Test
    void findFormUrlByDashboardId() {
        // given
        Dashboard dashboard = dashboardRepository.save(new Dashboard(club));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));

        // when
        ApplyFormUrlResponse applyFormUrlResponse = dashboardFacade.findFormUrlByDashboardId(dashboard.getId());

        // then
        assertAll(
                () -> assertThat(applyFormUrlResponse.postId()).isEqualTo(applyForm.getId()),
                () -> assertThat(applyFormUrlResponse.postUrl()).isEqualTo(applyForm.getUrl())
        );
    }

    @DisplayName("다건의 대시보드 정보를 조회한다.")
    @Test
    void findAllDashboardsByClubId() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend(club));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        Process firstProcess = processRepository.save(ProcessFixture.first(dashboard));
        Applicant failApplicant = ApplicantFixture.pendingDobby(firstProcess);
        failApplicant.reject();
        Applicant pendingApplicant1 = ApplicantFixture.pendingDobby(firstProcess);
        Applicant pendingApplicant2 = ApplicantFixture.pendingDobby(firstProcess);
        List<Applicant> applicants = List.of(failApplicant, pendingApplicant1, pendingApplicant2);
        applicantRepository.saveAll(applicants);

        // when
        DashboardsOfClubResponse dashboardsOfClubResponse = dashboardFacade.findAllDashboardsByClubId(club.getId());

        // then
        DashboardPreviewResponse dashboardPreview = dashboardsOfClubResponse.dashboardPreviewResponses().get(0);
        StatsResponse stats = dashboardPreview.stats();
        assertAll(
                () -> assertThat(dashboardsOfClubResponse.clubName()).isEqualTo(club.getName()),
                () -> assertThat(dashboardPreview.dashboardId()).isEqualTo(dashboard.getId()),
                () -> assertThat(dashboardPreview.title()).isEqualTo(applyForm.getTitle()),
                () -> assertThat(dashboardPreview.postUrl()).isEqualTo(applyForm.getUrl()),
                () -> assertThat(dashboardPreview.endDate()).isEqualTo(applyForm.getEndDate()),
                () -> assertThat(stats.accept()).isEqualTo(0),
                () -> assertThat(stats.fail()).isEqualTo(1),
                () -> assertThat(stats.inProgress()).isEqualTo(2)
        );
    }
}
