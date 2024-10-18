package com.cruru.applicant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.controller.request.ApplicantCreateRequest;
import com.cruru.applicant.controller.request.ApplicantMoveRequest;
import com.cruru.applicant.controller.request.ApplicantUpdateRequest;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.dto.ApplicantCard;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applicant.domain.repository.EvaluationRepository;
import com.cruru.applicant.exception.ApplicantNotFoundException;
import com.cruru.applicant.exception.badrequest.ApplicantRejectException;
import com.cruru.applicant.exception.badrequest.ApplicantUnrejectException;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.DefaultFilterAndOrderFixture;
import com.cruru.util.fixture.EvaluationFixture;
import com.cruru.util.fixture.ProcessFixture;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("지원자 서비스 테스트")
class ApplicantServiceTest extends ServiceTest {

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("지원자를 정상적으로 저장한다.")
    @Test
    void create() {
        // given
        Process firstProcess = processRepository.save(ProcessFixture.applyType());
        String name = "도비";
        String email = "kimdobby@email.com";
        String phone = "01052525252";
        ApplicantCreateRequest createRequest = new ApplicantCreateRequest(name, email, phone);

        // when
        Applicant createdApplicant = applicantService.create(createRequest, firstProcess);

        // then
        Applicant actualApplicant = applicantRepository.findById(createdApplicant.getId()).get();
        assertAll(
                () -> assertThat(actualApplicant.getName()).isEqualTo(name),
                () -> assertThat(actualApplicant.getEmail()).isEqualTo(email),
                () -> assertThat(actualApplicant.getPhone()).isEqualTo(phone)
        );
    }

    @DisplayName("프로세스 내의 모든 지원자를 조회한다.")
    @Test
    void findAllByProcess() {
        // given
        Process process = processRepository.save(ProcessFixture.applyType());
        Applicant applicant1 = applicantRepository.save(ApplicantFixture.pendingDobby(process));
        Applicant applicant2 = applicantRepository.save(ApplicantFixture.pendingDobby(process));
        Applicant applicant3 = applicantRepository.save(ApplicantFixture.pendingDobby(process));
        List<Applicant> applicants = List.of(applicant1, applicant2, applicant3);

        // when
        List<Applicant> applicantsInProcess = applicantService.findAllByProcess(process);

        // then
        assertThat(applicantsInProcess).containsExactlyElementsOf(applicants);
    }

    @DisplayName("프로세스 내의 모든 지원자를 필터링 및 정렬하여 조회한다.")
    @Test
    void findAllByProcess_filterAndOrder() {
        // given
        Process process1 = processRepository.save(ProcessFixture.applyType());
        Process process2 = processRepository.save(ProcessFixture.applyType());
        List<Process> processes = List.of(process1, process2);

        Applicant applicant1 = applicantRepository.save(ApplicantFixture.pendingDobby(process1));
        Applicant applicant2 = applicantRepository.save(ApplicantFixture.pendingRush(process1));
        applicantRepository.save(ApplicantFixture.pendingDobby(process2));

        evaluationRepository.save(EvaluationFixture.fivePoints(process1, applicant1));
        evaluationRepository.save(EvaluationFixture.fourPoints(process1, applicant2));

        String evaluationStatus = "EVALUATED";
        String sortByCreatedAt = "DESC";
        String sortByScore = null;

        // when
        List<ApplicantCard> applicantCards = applicantService.findApplicantCards(
                processes,
                DefaultFilterAndOrderFixture.DEFAULT_MIN_SCORE,
                DefaultFilterAndOrderFixture.DEFAULT_MAX_SCORE,
                evaluationStatus,
                sortByCreatedAt,
                sortByScore
        );

        // then
        assertThat(applicantCards).hasSize(2);
        assertThat(applicantCards.get(0).id()).isEqualTo(applicant2.getId());
        assertThat(applicantCards.get(1).id()).isEqualTo(applicant1.getId());
    }

    @DisplayName("id에 해당하는 지원자가 존재하지 않으면 Not Found 예외가 발생한다.")
    @Test
    void findById_notFound() {
        // given
        long invalidId = -1L;

        // when&then
        assertThatThrownBy(() -> applicantService.findById(invalidId))
                .isInstanceOf(ApplicantNotFoundException.class);
    }

    @DisplayName("지원자의 이름, 이메일, 전화번호 변경 요청시, 정보를 업데이트한다")
    @Test
    void updateApplicantInformation() {
        // given
        Applicant applicant = ApplicantFixture.pendingDobby();
        String changedName = "수정된 이름";
        String changedEmail = "modified@email.com";
        String changedPhone = "01012341234";
        ApplicantUpdateRequest updateRequest = new ApplicantUpdateRequest(changedName, changedEmail, changedPhone);
        Applicant savedApplicant = applicantRepository.save(applicant);

        // when
        applicantService.updateApplicantInformation(savedApplicant.getId(), updateRequest);

        // then
        Applicant updatedApplicant = applicantRepository.findById(savedApplicant.getId()).get();
        assertAll(
                () -> assertThat(changedName).isEqualTo(updatedApplicant.getName()),
                () -> assertThat(changedEmail).isEqualTo(updatedApplicant.getEmail()),
                () -> assertThat(changedPhone).isEqualTo(updatedApplicant.getPhone())
        );
    }

    @DisplayName("여러 건의 지원서를 요청된 프로세스로 일괄 변경한다.")
    @Test
    void moveApplicantProcess() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        Process beforeProcess = processRepository.save(ProcessFixture.applyType(dashboard));
        Process afterProcess = processRepository.save(ProcessFixture.approveType(dashboard));

        List<Applicant> applicants = applicantRepository.saveAll(List.of(
                ApplicantFixture.pendingDobby(beforeProcess),
                ApplicantFixture.pendingDobby(beforeProcess)
        ));
        List<Long> applicantIds = applicants.stream()
                .map(Applicant::getId)
                .toList();
        ApplicantMoveRequest moveRequest = new ApplicantMoveRequest(applicantIds);

        // when
        applicantService.moveApplicantProcess(afterProcess, moveRequest);

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
        applicantService.reject(applicant.getId());

        // then
        Applicant rejectedApplicant = applicantRepository.findById(applicant.getId()).get();
        assertThat(rejectedApplicant.isRejected()).isTrue();
    }

    @DisplayName("이미 불합격한 지원자를 불합격시키려 하면 예외가 발생한다.")
    @Test
    void reject_alreadyRejected() {
        // given
        Applicant rejectedApplicant = applicantRepository.save(ApplicantFixture.rejectedRush());

        // when&then
        Long applicantId = rejectedApplicant.getId();
        assertThatThrownBy(() -> applicantService.reject(applicantId))
                .isInstanceOf(ApplicantRejectException.class);
    }

    @DisplayName("특정 지원자의 불합격을 취소한다.")
    @Test
    void unreject() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.rejectedRush());

        // when
        applicantService.unreject(applicant.getId());

        // then
        Applicant unrejectedApplicant = applicantRepository.findById(applicant.getId()).get();
        assertThat(unrejectedApplicant.isNotRejected()).isTrue();
    }

    @DisplayName("불합격이 아닌 지원자의 불합격을 취소하면 예외가 발생한다.")
    @Test
    void unreject_notRejected() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingRush());

        // when&then
        Long applicantId = applicant.getId();
        assertThatThrownBy(() -> applicantService.unreject(applicantId))
                .isInstanceOf(ApplicantUnrejectException.class);
    }

    @DisplayName("입력된 지원자들을 삭제한다.")
    @Test
    void deleteAllInBatch() {
        // given
        Applicant applicant1 = applicantRepository.save(ApplicantFixture.pendingDobby());
        Applicant applicant2 = applicantRepository.save(ApplicantFixture.pendingDobby());
        Applicant applicant3 = applicantRepository.save(ApplicantFixture.pendingDobby());
        List<Applicant> applicants = List.of(applicant1, applicant2);

        // when
        applicantService.deleteAllInBatch(applicants);

        // then
        assertThat(applicantRepository.findAll()).contains(applicant3)
                .doesNotContain(applicant1, applicant2);
    }
}
