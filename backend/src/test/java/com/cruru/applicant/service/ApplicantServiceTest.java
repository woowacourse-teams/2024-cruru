package com.cruru.applicant.service;

import static com.cruru.applicant.domain.ApplicantState.REJECTED;
import static com.cruru.util.fixture.ApplicantFixture.createPendingApplicantDobby;
import static com.cruru.util.fixture.DashboardFixture.createBackendDashboard;
import static com.cruru.util.fixture.ProcessFixture.createFinalProcess;
import static com.cruru.util.fixture.ProcessFixture.createFirstProcess;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        Process process = processRepository.save(createFirstProcess());
        Applicant applicant1 = applicantRepository.save(createPendingApplicantDobby(process));
        Applicant applicant2 = applicantRepository.save(createPendingApplicantDobby(process));
        Applicant applicant3 = applicantRepository.save(createPendingApplicantDobby(process));
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

    @DisplayName("지원자의 정보 변경 요청의 이름, 이메일, 전화번호 변경점이 없다면 예외를 던진다")
    @Test
    void updateApplicantInformation() {
        // given
        Applicant applicant = ApplicantFixture.createPendingApplicantDobby();
        String originalName = applicant.getName();
        String originalEmail = applicant.getEmail();
        String originalPhone = applicant.getPhone();
        ApplicantUpdateRequest noChangeRequest = new ApplicantUpdateRequest(originalName, originalEmail, originalPhone);

        // when
        Applicant savedApplicant = applicantRepository.save(applicant);

        // then
        assertThatThrownBy(() -> applicantService.updateApplicantInformation(noChangeRequest,
                savedApplicant
        )).isInstanceOf(ApplicantNoChangeException.class);
    }

    @DisplayName("여러 건의 지원서를 요청된 프로세스로 일괄 변경한다.")
    @Test
    void moveApplicantProcess() {
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
        applicantService.moveApplicantProcess(afterProcess, moveRequest);

        // then
        List<Applicant> actualApplicants = entityManager.createQuery("SELECT a FROM Applicant a JOIN FETCH a.process",
                Applicant.class
        ).getResultList();
        assertThat(actualApplicants).allMatch(applicant -> applicant.getProcess().equals(afterProcess));
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
        Applicant targetApplicant = createPendingApplicantDobby();
        targetApplicant.reject();
        Applicant rejectedApplicant = applicantRepository.save(targetApplicant);

        // when&then
        Long applicantId = rejectedApplicant.getId();
        assertThatThrownBy(() -> applicantService.reject(applicantId)).isInstanceOf(ApplicantRejectException.class);
    }
}
