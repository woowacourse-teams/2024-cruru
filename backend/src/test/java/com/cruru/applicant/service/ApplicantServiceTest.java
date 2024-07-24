package com.cruru.applicant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.answer.domain.Answer;
import com.cruru.answer.domain.repository.AnswerRepository;
import com.cruru.applicant.controller.dto.ApplicantDetailResponse;
import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.controller.dto.ApplicantResponse;
import com.cruru.applicant.controller.dto.QnaResponse;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applicant.exception.ApplicantNotFoundException;
import com.cruru.applicant.exception.ApplicantRejectException;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ServiceTest;
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
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("여러 건의 지원서를 요청된 프로세스로 일괄 변경한다.")
    @Test
    void updateApplicantProcess() {
        // given
        Dashboard dashboard = dashboardRepository.save(new Dashboard("모집 공고1", null));
        Process beforeProcess = processRepository.save(new Process(0, "이전 프로세스", "프로세스 설명1", dashboard));
        Process afterProcess = processRepository.save(new Process(1, "이후 프로세스", "프로세스 설명2", dashboard));

        List<Applicant> applicants = applicantRepository.saveAll(List.of(
                new Applicant(null, null, null, beforeProcess, false),
                new Applicant(null, null, null, beforeProcess, false)
        ));
        List<Long> applicantIds = applicants.stream()
                .map(Applicant::getId)
                .toList();
        ApplicantMoveRequest moveRequest = new ApplicantMoveRequest(applicantIds);

        // when
        applicantService.updateApplicantProcess(afterProcess.getId(), moveRequest);

        // then
        List<Applicant> actualApplicants = entityManager.createQuery(
                        "SELECT a FROM Applicant a JOIN FETCH a.process", Applicant.class)
                .getResultList();
        assertThat(actualApplicants).allMatch(applicant -> applicant.getProcess().equals(afterProcess));
    }

    @DisplayName("id로 지원자를 찾는다.")
    @Test
    void findById() {
        // given
        Process process = processRepository.save(new Process(0, "서류", "서류 전형", null));
        Applicant applicant = new Applicant(1L, "명오", "myun@mail.com", "01012341234", process, false);
        applicant = applicantRepository.save(applicant);

        // when
        ApplicantResponse found = applicantService.findById(applicant.getId());

        // then
        assertThat(applicant.getId()).isEqualTo(found.id());
    }

    @DisplayName("id에 해당하는 지원자가 존재하지 않으면 Not Found 예외가 발생한다.")
    @Test
    void findById_notFound() {
        // given
        long invalidId = -1L;

        // given&when&then
        assertThatThrownBy(() -> applicantService.findById(invalidId))
                .isInstanceOf(ApplicantNotFoundException.class);
    }

    @DisplayName("id로 지원자의 상세 정보를 찾는다.")
    @Test
    void findDetailById() {
        // given
        Process process = new Process(0, "서류", "서류 단계", null);
        processRepository.save(process);
        Applicant applicant = new Applicant(1L, "명오", "myun@mail.com", "01012341234", process, false);
        applicantRepository.save(applicant);

        Question question = new Question("좋아하는 동물은?", 0, null);
        questionRepository.save(question);
        Answer answer = new Answer("토끼", question, applicant);
        answerRepository.save(answer);

        // when
        ApplicantDetailResponse applicantDetailResponse = applicantService.findDetailById(applicant.getId());

        //then
        List<QnaResponse> qnaResponses = applicantDetailResponse.qnaResponses();
        assertAll(
                () -> assertThat(qnaResponses.get(0).question()).isEqualTo(question.getContent()),
                () -> assertThat(qnaResponses.get(0).answer()).isEqualTo(answer.getContent())
        );
    }

    @DisplayName("id로 지원자를 불합격시킨다.")
    @Test
    void reject() {
        // given
        Applicant applicant = applicantRepository.save(new Applicant("name", "email", "phone", null, false));

        // when
        applicantService.reject(applicant.getId());

        // then
        assertThat(applicantRepository.findById(applicant.getId()).get().getIsRejected()).isTrue();
    }

    @DisplayName("이미 불합격한 지원자를 불합격시키려 하면 예외가 발생한다.")
    @Test
    void reject_alreadyRejected() {
        // given
        Applicant applicant = applicantRepository.save(new Applicant("name", "email", "phone", null, true));

        // when&then
        assertThatThrownBy(() -> applicantService.reject(applicant.getId()))
                .isInstanceOf(ApplicantRejectException.class);
    }
}
