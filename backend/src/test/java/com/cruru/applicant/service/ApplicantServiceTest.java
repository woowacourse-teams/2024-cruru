package com.cruru.applicant.service;

import static com.cruru.applicant.domain.ApplicantState.REJECTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.controller.dto.ApplicantUpdateRequest;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applicant.exception.ApplicantNotFoundException;
import com.cruru.applicant.exception.badrequest.ApplicantNoChangeException;
import com.cruru.applicant.exception.badrequest.ApplicantRejectException;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.DashboardFixture;
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
    private EntityManager entityManager;

    @DisplayName("프로세스 내의 모든 지원자를 조회한다.")
    @Test
    void findAllByProcess() {
        // given
        Process process = processRepository.save(ProcessFixture.createFirstProcess());
        Applicant applicant1 = applicantRepository.save(ApplicantFixture.createPendingApplicantDobby(process));
        Applicant applicant2 = applicantRepository.save(ApplicantFixture.createPendingApplicantDobby(process));
        Applicant applicant3 = applicantRepository.save(ApplicantFixture.createPendingApplicantDobby(process));
        List<Applicant> applicants = List.of(applicant1, applicant2, applicant3);

        // when
        List<Applicant> applicantsInProcess = applicantService.findAllByProcess(process);

        // then
        assertThat(applicantsInProcess).containsExactlyElementsOf(applicants);
    }

    @DisplayName("id에 해당하는 지원자가 존재하지 않으면 Not Found 예외가 발생한다.")
    @Test
    void findById_notFound() {
        // given
        long invalidId = -1L;

        // when&then
        assertThatThrownBy(() -> applicantService.findById(invalidId)).isInstanceOf(ApplicantNotFoundException.class);
    }

    @DisplayName("지원자의 이름, 이메일, 전화번호 변경 요청시, 정보를 업데이트한다")
    @Test
    void updateApplicantInformation() {
        // given
        Applicant applicant = ApplicantFixture.createPendingApplicantDobby();
        String changedName = "수정된 이름";
        String changedEmail = "modified@email.com";
        String changedPhone = "010xxxxxxxx";
        ApplicantUpdateRequest updateRequest = new ApplicantUpdateRequest(changedName, changedEmail, changedPhone);
        Applicant savedApplicant = applicantRepository.save(applicant);

        // when
        applicantService.updateApplicantInformation(savedApplicant.getId(), updateRequest);

        // then
        Applicant updatedApplicant = applicantRepository.findById(savedApplicant.getId()).get();
        assertAll(() -> {
            assertThat(changedName).isEqualTo(updatedApplicant.getName());
            assertThat(changedEmail).isEqualTo(updatedApplicant.getEmail());
            assertThat(changedPhone).isEqualTo(updatedApplicant.getPhone());
        });
    }

    @DisplayName("지원자의 정보 변경 요청의 이름, 이메일, 전화번호 변경점이 없다면 예외를 던진다")
    @Test
    void updateApplicantInformation_ThrowsException() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.createPendingApplicantDobby());
        String originalName = applicant.getName();
        String originalEmail = applicant.getEmail();
        String originalPhone = applicant.getPhone();
        ApplicantUpdateRequest noChangeRequest = new ApplicantUpdateRequest(originalName, originalEmail, originalPhone);
        Long applicantId = applicant.getId();

        // when & then
        assertThatThrownBy(() -> {
            applicantService.updateApplicantInformation(applicantId, noChangeRequest);
        }).isInstanceOf(ApplicantNoChangeException.class);
    }

    @DisplayName("여러 건의 지원서를 요청된 프로세스로 일괄 변경한다.")
    @Test
    void moveApplicantProcess() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.createBackendDashboard());
        Process beforeProcess = processRepository.save(ProcessFixture.createFirstProcess(dashboard));
        Process afterProcess = processRepository.save(ProcessFixture.createFinalProcess(dashboard));

        List<Applicant> applicants = applicantRepository.saveAll(List.of(
                ApplicantFixture.createPendingApplicantDobby(
                        beforeProcess),
                ApplicantFixture.createPendingApplicantDobby(beforeProcess)
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
        assertAll(() -> {
            assertThat(actualApplicants).isNotEmpty();
            assertThat(actualApplicants).allMatch(applicant -> applicant.getProcess().equals(afterProcess));
        });
    }

    @DisplayName("특정 지원자의 상태를 불합격으로 변경한다.")
    @Test
    void reject() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.createPendingApplicantDobby());

        // when
        applicantService.reject(applicant.getId());

        // then
        Applicant rejectedApplicant = applicantRepository.findById(applicant.getId()).get();
        assertThat(rejectedApplicant.getState()).isEqualTo(REJECTED);
    }

    @DisplayName("이미 불합격한 지원자를 불합격시키려 하면 예외가 발생한다.")
    @Test
    void reject_alreadyRejected() {
        // given
        Applicant targetApplicant = ApplicantFixture.createPendingApplicantDobby();
        targetApplicant.reject();
        Applicant rejectedApplicant = applicantRepository.save(targetApplicant);

        // when&then
        Long applicantId = rejectedApplicant.getId();
        assertThatThrownBy(() -> applicantService.reject(applicantId)).isInstanceOf(ApplicantRejectException.class);
    }
}
