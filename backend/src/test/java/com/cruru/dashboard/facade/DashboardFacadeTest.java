package com.cruru.dashboard.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.Evaluation;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applicant.domain.repository.EvaluationRepository;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.dashboard.controller.request.DashboardCreateRequest;
import com.cruru.dashboard.controller.response.DashboardCreateResponse;
import com.cruru.dashboard.controller.response.DashboardPreviewResponse;
import com.cruru.dashboard.controller.response.DashboardsOfClubResponse;
import com.cruru.dashboard.controller.response.StatsResponse;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.dashboard.exception.DashboardNotFoundException;
import com.cruru.email.domain.Email;
import com.cruru.email.domain.repository.EmailRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.controller.request.ChoiceCreateRequest;
import com.cruru.question.controller.request.QuestionCreateRequest;
import com.cruru.question.domain.Answer;
import com.cruru.question.domain.Choice;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.AnswerRepository;
import com.cruru.question.domain.repository.ChoiceRepository;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.AnswerFixture;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.ChoiceFixture;
import com.cruru.util.fixture.ClubFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.EmailFixture;
import com.cruru.util.fixture.EvaluationFixture;
import com.cruru.util.fixture.LocalDateFixture;
import com.cruru.util.fixture.ProcessFixture;
import com.cruru.util.fixture.QuestionFixture;
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

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ChoiceRepository choiceRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EmailRepository emailRepository;

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
        DashboardCreateResponse response = dashboardFacade.create(club.getId(), request);

        // then
        assertThat(dashboardRepository.findById(response.dashboardId())).isPresent();
    }

    @DisplayName("다건의 대시보드 정보를 조회한다.")
    @Test
    void findAllDashboardsByClubId() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend(club));
        Dashboard dashboard1 = dashboardRepository.save(DashboardFixture.backend(club));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        ApplyForm applyForm1 = applyFormRepository.save(ApplyFormFixture.backend(dashboard1));
        Process firstProcess = processRepository.save(ProcessFixture.applyType(dashboard));
        Process lastProcess = processRepository.save(ProcessFixture.approveType(dashboard));
        Process firstProcess1 = processRepository.save(ProcessFixture.applyType(dashboard1));
        Process lastProcess1 = processRepository.save(ProcessFixture.approveType(dashboard1));

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

        List<Applicant> applicants1 = List.of(
                ApplicantFixture.rejectedRush(lastProcess1),
                ApplicantFixture.rejectedRush(firstProcess1),
                ApplicantFixture.pendingDobby(lastProcess1),
                ApplicantFixture.pendingDobby(firstProcess1),
                ApplicantFixture.pendingDobby(firstProcess1),
                ApplicantFixture.pendingDobby(firstProcess1)
        );
        applicantRepository.saveAll(applicants1);

        // when
        DashboardsOfClubResponse dashboardsOfClubResponse =
                dashboardFacade.findAllDashboardsByClubId(club.getId());

        // then
        DashboardPreviewResponse dashboardPreview = dashboardsOfClubResponse.dashboardPreviewResponses().get(0);
        StatsResponse stats = dashboardPreview.stats();
        assertAll(
                () -> assertThat(dashboardsOfClubResponse.clubName()).isEqualTo(club.getName()),
                () -> assertThat(dashboardPreview.dashboardId()).isEqualTo(dashboard.getId()),
                () -> assertThat(dashboardPreview.title()).isEqualTo(applyForm.getTitle()),
                () -> assertThat(dashboardPreview.applyFormId()).isEqualTo(String.valueOf(applyForm.getId())),
                () -> assertThat(dashboardPreview.endDate()).isEqualTo(applyForm.getEndDate()),
                () -> assertThat(stats.accept()).isEqualTo(1),
                () -> assertThat(stats.fail()).isEqualTo(2),
                () -> assertThat(stats.inProgress()).isEqualTo(3)
        );
    }

    @DisplayName("대시보드를 삭제한다.")
    @Test
    void delete() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend(club));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        Question question = questionRepository.save(QuestionFixture.singleChoiceType(applyForm));
        Choice choice = choiceRepository.save(ChoiceFixture.first(question));
        Process process = processRepository.save(ProcessFixture.applyType(dashboard));
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby(process));
        Answer answer = answerRepository.save(AnswerFixture.first(question, applicant));
        Email email = emailRepository.save(EmailFixture.rejectEmail(club, applicant));
        Evaluation evaluation = evaluationRepository.save(EvaluationFixture.fivePoints(process, applicant));

        // when
        dashboardFacade.delete(dashboard.getId());

        // then
        assertAll(
                () -> assertThat(evaluationRepository.findAll()).doesNotContain(evaluation),
                () -> assertThat(emailRepository.findAll()).doesNotContain(email),
                () -> assertThat(answerRepository.findAll()).doesNotContain(answer),
                () -> assertThat(applicantRepository.findAll()).doesNotContain(applicant),
                () -> assertThat(processRepository.findAll()).doesNotContain(process),
                () -> assertThat(choiceRepository.findAll()).doesNotContain(choice),
                () -> assertThat(questionRepository.findAll()).doesNotContain(question),
                () -> assertThat(applyFormRepository.findAll()).doesNotContain(applyForm),
                () -> assertThat(dashboardRepository.findAll()).doesNotContain(dashboard)
        );
    }

    @DisplayName("존재하지 않는 대시보드를 삭제하면 예외가 발생한다.")
    @Test
    void delete_notFound() {
        // given
        long invalidId = -1;

        // when && then
        assertThatThrownBy(() -> dashboardFacade.delete(invalidId))
                .isInstanceOf(DashboardNotFoundException.class);
    }
}
