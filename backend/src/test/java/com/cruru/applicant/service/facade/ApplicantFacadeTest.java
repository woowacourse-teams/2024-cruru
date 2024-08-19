package com.cruru.applicant.service.facade;

import static com.cruru.applicant.domain.ApplicantState.PENDING;
import static com.cruru.applicant.domain.ApplicantState.REJECTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.answer.domain.Answer;
import com.cruru.answer.domain.repository.AnswerRepository;
import com.cruru.answer.dto.AnswerResponse;
import com.cruru.applicant.controller.dto.ApplicantAnswerResponses;
import com.cruru.applicant.controller.dto.ApplicantBasicResponse;
import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.controller.dto.ApplicantResponse;
import com.cruru.applicant.controller.dto.ApplicantUpdateRequest;
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
import com.cruru.util.fixture.AnswerFixture;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.ProcessFixture;
import com.cruru.util.fixture.QuestionFixture;
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
        Process process = processRepository.save(ProcessFixture.first());
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby(process));

        // when
        ApplicantBasicResponse basicResponse = applicantFacade.readBasicById(applicant.getId());
        ProcessSimpleResponse processResponse = basicResponse.processResponse();
        ApplicantResponse applicantResponse = basicResponse.applicantResponse();

        // then
        assertAll(
                () -> assertThat(processResponse.id()).isEqualTo(process.getId()),
                () -> assertThat(processResponse.name()).isEqualTo(process.getName()),
                () -> assertThat(applicantResponse.id()).isEqualTo(applicant.getId()),
                () -> assertThat(applicantResponse.name()).isEqualTo(applicant.getName()),
                () -> assertThat(applicantResponse.email()).isEqualTo(applicant.getEmail()),
                () -> assertThat(applicantResponse.phone()).isEqualTo(applicant.getPhone())
        );
    }

    @DisplayName("id로 지원자의 상세 정보를 찾는다.")
    @Test
    void readDetailById() {
        // given
        Dashboard dashboard = DashboardFixture.backend();
        dashboardRepository.save(dashboard);
        Process process = ProcessFixture.first(dashboard);
        processRepository.save(process);
        Applicant applicant = ApplicantFixture.pendingDobby(process);
        applicantRepository.save(applicant);

        Question question = questionRepository.save(QuestionFixture.shortAnswerType(null));
        questionRepository.save(question);
        Answer answer = AnswerFixture.simple(question, applicant);
        answerRepository.save(answer);

        // when
        ApplicantAnswerResponses applicantAnswerResponses = applicantFacade.readDetailById(applicant.getId());

        //then
        List<AnswerResponse> answerResponses = applicantAnswerResponses.answerResponses();
        assertAll(
                () -> assertThat(answerResponses.get(0).question()).isEqualTo(question.getContent()),
                () -> assertThat(answerResponses.get(0).answer()).isEqualTo(answer.getContent())
        );
    }

    @DisplayName("지원자의 이름, 이메일, 전화번호를 변경한다.")
    @Test
    void updateApplicantInformation() {
        // given
        Applicant applicant = ApplicantFixture.pendingDobby();
        String changedName = "수정된 이름";
        String changedEmail = "modified@email.com";
        String changedPhone = "01099999999";
        ApplicantUpdateRequest changeRequest = new ApplicantUpdateRequest(changedName, changedEmail, changedPhone);
        Applicant savedApplicant = applicantRepository.save(applicant);
        Long applicantId = savedApplicant.getId();

        // when
        applicantFacade.updateApplicantInformation(applicantId, changeRequest);

        // then
        Applicant actualApplicant = applicantRepository.findById(applicantId).get();
        assertAll(
                () -> assertThat(actualApplicant.getName()).isEqualTo(changedName),
                () -> assertThat(actualApplicant.getEmail()).isEqualTo(changedEmail),
                () -> assertThat(actualApplicant.getPhone()).isEqualTo(changedPhone)
        );
    }

    @DisplayName("복수의 지원서들을 요청된 프로세스로 일괄 업데이트한다.")
    @Test
    void updateApplicantProcess() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        Process beforeProcess = processRepository.save(ProcessFixture.first(dashboard));
        Process afterProcess = processRepository.save(ProcessFixture.last(dashboard));

        List<Applicant> applicants = applicantRepository.saveAll(
                List.of(
                        ApplicantFixture.pendingDobby(beforeProcess),
                        ApplicantFixture.pendingDobby(beforeProcess)
                ));
        List<Long> applicantIds = applicants.stream()
                .map(Applicant::getId)
                .toList();
        ApplicantMoveRequest moveRequest = new ApplicantMoveRequest(applicantIds);

        // when
        applicantFacade.updateApplicantProcess(afterProcess.getId(), moveRequest);

        // then
        List<Applicant> actualApplicants = entityManager.createQuery(
                "SELECT a FROM Applicant a JOIN FETCH a.process",
                Applicant.class
        ).getResultList();
        assertAll(
                () -> assertThat(actualApplicants).isNotEmpty(),
                () -> assertThat(actualApplicants).allMatch(applicant -> applicant.getProcess().equals(afterProcess))
        );
    }

    @DisplayName("특정 지원자의 상태를 불합격으로 변경한다.")
    @Test
    void reject() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby());

        // when
        applicantFacade.reject(applicant.getId());

        // then
        Applicant rejectedApplicant = applicantRepository.findById(applicant.getId()).get();
        assertThat(rejectedApplicant.getState()).isEqualTo(REJECTED);
    }

    @DisplayName("특정 지원자의 상태를 불합격에서 해제한다.")
    @Test
    void unreject() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.rejectedRush());

        // when
        applicantFacade.unreject(applicant.getId());

        // then
        Applicant rejectedApplicant = applicantRepository.findById(applicant.getId()).get();
        assertThat(rejectedApplicant.getState()).isEqualTo(PENDING);
    }
}
