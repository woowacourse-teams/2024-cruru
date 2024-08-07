package com.cruru.applicant.service.facade;

import static com.cruru.applicant.domain.ApplicantState.REJECTED;
import static com.cruru.util.fixture.ApplicantFixture.createPendingApplicantDobby;
import static com.cruru.util.fixture.DashboardFixture.createBackendDashboard;
import static com.cruru.util.fixture.ProcessFixture.createFinalProcess;
import static com.cruru.util.fixture.ProcessFixture.createFirstProcess;
import static com.cruru.util.fixture.QuestionFixture.createShortAnswerQuestion;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.answer.domain.Answer;
import com.cruru.answer.domain.repository.AnswerRepository;
import com.cruru.applicant.controller.dto.ApplicantBasicResponse;
import com.cruru.applicant.controller.dto.ApplicantDetailResponse;
import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.controller.dto.ApplicantResponse;
import com.cruru.applicant.controller.dto.ApplicantUpdateRequest;
import com.cruru.applicant.controller.dto.QnaResponse;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.controller.dto.ProcessSimpleResponse;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplicantFixture;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("지원자 파사드 서비스 테스트")
class ApplicantFacadeTest extends ServiceTest {

    @Autowired
    private ApplicantFacade applicantFacade;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("Id로 지원자의 기본 정보를 조회한다.")
    @Test
    void readBasicById() {
        // given
        Process process = processRepository.save(createFirstProcess());
        Applicant applicant = applicantRepository.save(createPendingApplicantDobby(process));

        // when
        ApplicantBasicResponse basicResponse = applicantFacade.readBasicById(applicant.getId());
        ProcessSimpleResponse processResponse = basicResponse.processResponse();
        ApplicantResponse applicantResponse = basicResponse.applicantResponse();

        // then
        assertAll(() -> assertThat(process.getId()).isEqualTo(processResponse.id()),
                () -> assertThat(process.getName()).isEqualTo(processResponse.name()),
                () -> assertThat(applicant.getId()).isEqualTo(applicantResponse.id()),
                () -> assertThat(applicant.getName()).isEqualTo(applicantResponse.name()),
                () -> assertThat(applicant.getEmail()).isEqualTo(applicantResponse.email()),
                () -> assertThat(applicant.getPhone()).isEqualTo(applicantResponse.phone())
        );
    }

    @DisplayName("id로 지원자의 상세 정보를 찾는다.")
    @Test
    void readDetailById() {
        // given
        Dashboard dashboard = createBackendDashboard();
        dashboardRepository.save(dashboard);
        Process process = createFirstProcess(dashboard);
        processRepository.save(process);
        Applicant applicant = createPendingApplicantDobby(process);
        applicantRepository.save(applicant);

        Question question = questionRepository.save(createShortAnswerQuestion(null));
        questionRepository.save(question);
        Answer answer = new Answer("토끼", question, applicant);
        answerRepository.save(answer);

        // when
        ApplicantDetailResponse applicantDetailResponse = applicantFacade.readDetailById(applicant.getId());

        //then
        List<QnaResponse> qnaResponses = applicantDetailResponse.qnaResponses();
        assertAll(() -> assertThat(qnaResponses.get(0).question()).isEqualTo(question.getContent()),
                () -> assertThat(qnaResponses.get(0).answer()).isEqualTo(answer.getContent())
        );
    }

    @DisplayName("지원자의 이름, 이메일, 전화번호를 변경한다.")
    @Test
    void updateApplicantInformation() {
        // given
        Applicant applicant = ApplicantFixture.createPendingApplicantDobby();
        String changedName = "수정된 이름";
        String changedEmail = "modified@email.com";
        String changedPhone = "01099999999";
        ApplicantUpdateRequest changeRequest = new ApplicantUpdateRequest(changedName, changedEmail, changedPhone);
        Applicant savedApplicant = applicantRepository.save(applicant);
        Long applicantId = savedApplicant.getId();

        // when
        applicantFacade.updateApplicantInformation(changeRequest, applicantId);

        // then
        Applicant actualApplicant = applicantRepository.findById(applicantId).get();
        assertAll(() -> {
            assertThat(actualApplicant.getName()).isEqualTo(changedName);
            assertThat(actualApplicant.getEmail()).isEqualTo(changedEmail);
            assertThat(actualApplicant.getPhone()).isEqualTo(changedPhone);
        });
    }

    @DisplayName("복수의 지원서들을 요청된 프로세스로 일괄 업데이트한다.")
    @Test
    void updateApplicantProcess() {
        // given
        Dashboard dashboard = dashboardRepository.save(createBackendDashboard());
        Process beforeProcess = processRepository.save(createFirstProcess(dashboard));
        Process afterProcess = processRepository.save(createFinalProcess(dashboard));

        List<Applicant> applicants = applicantRepository.saveAll(List.of(createPendingApplicantDobby(beforeProcess),
                createPendingApplicantDobby(beforeProcess)
        ));
        List<Long> applicantIds = applicants.stream()
                .map(Applicant::getId)
                .toList();
        ApplicantMoveRequest moveRequest = new ApplicantMoveRequest(applicantIds);

        // when
        applicantFacade.updateApplicantProcess(afterProcess.getId(), moveRequest);

        // then
        List<Applicant> actualApplicants = entityManager.createQuery("SELECT a FROM Applicant a JOIN FETCH a.process",
                Applicant.class
        ).getResultList();
        assertThat(actualApplicants).allMatch(applicant -> applicant.getProcess().equals(afterProcess));
    }

    @DisplayName("특정 지원자의 상태를 불합격으로 변경한다.")
    @Test
    void updateApplicantToReject() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.createPendingApplicantDobby());

        // when
        applicantFacade.updateApplicantToReject(applicant.getId());

        // then
        Applicant rejectedApplicant = applicantRepository.findById(applicant.getId()).get();
        assertThat(rejectedApplicant.getState()).isEqualTo(REJECTED);
    }
}
