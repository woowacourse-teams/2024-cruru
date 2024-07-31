package com.cruru.applyform.service;

import static com.cruru.question.domain.QuestionType.SHORT_ANSWER;
import static com.cruru.util.fixture.ApplyFormFixture.createFrontendApplyForm;
import static com.cruru.util.fixture.DashboardFixture.createBackendDashboard;
import static com.cruru.util.fixture.ProcessFixture.createFirstProcess;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cruru.advice.InternalServerException;
import com.cruru.answer.domain.repository.AnswerRepository;
import com.cruru.applicant.controller.dto.ApplicantCreateRequest;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.controller.dto.AnswerCreateRequest;
import com.cruru.applyform.controller.dto.ApplyFormSubmitRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.applyform.exception.ApplyFormNotFoundException;
import com.cruru.applyform.exception.PersonalDataProcessingException;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("지원서 폼 서비스 테스트")
class ApplyFormServiceTest extends ServiceTest {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ApplyFormService applyFormService;

    @DisplayName("지원서 폼 제출에 성공한다.")
    @Test
    void submit() {
        // given
        Dashboard dashboard = dashboardRepository.save(createBackendDashboard());
        Process firstProcess = processRepository.save(createFirstProcess(dashboard));
        ApplyForm applyForm = applyFormRepository.save(createFrontendApplyForm(dashboard));
        Question question1 = questionRepository.save(new Question(SHORT_ANSWER, "자기소개 부탁드려요", 0, applyForm));
        Question question2 = questionRepository.save(new Question(SHORT_ANSWER, "지원 경로가 어떻게 되나요?", 1, applyForm));
        List<AnswerCreateRequest> answerCreateRequests = List.of(
                new AnswerCreateRequest(question1.getId(), List.of("안녕하세요, 맛있는 초코칩입니다.")),
                new AnswerCreateRequest(question2.getId(), List.of("온라인"))
        );
        ApplyFormSubmitRequest request = new ApplyFormSubmitRequest(
                new ApplicantCreateRequest("초코칩", "dev.chocochip@gmail.com", "01000000000"),
                answerCreateRequests,
                true);

        // when
        applyFormService.submit(request, applyForm.getId());

        // then
        assertThat(answerRepository.findAll()).hasSize(answerCreateRequests.size());
        assertThat(applicantRepository.countByProcess(firstProcess)).isEqualTo(1);
    }

    @DisplayName("지원서 폼 제출 시, 대시보드에 프로세스가 존재하지 않으면 예외가 발생한다.")
    @Test
    void submit_dashboardWithNoProcess() {
        // given
        Dashboard dashboard = dashboardRepository.save(createBackendDashboard());
        ApplyForm applyForm = applyFormRepository.save(createFrontendApplyForm(dashboard));
        Question question = questionRepository.save(new Question(SHORT_ANSWER, "지원 경로가 어떻게 되나요?", 0, applyForm));

        ApplyFormSubmitRequest request = new ApplyFormSubmitRequest(
                new ApplicantCreateRequest("초코칩", "dev.chocochip@gmail.com", "01000000000"),
                List.of(new AnswerCreateRequest(question.getId(), List.of("온라인"))),
                true);

        // when&then
        assertThatThrownBy(() -> applyFormService.submit(request, applyForm.getId()))
                .isInstanceOf(InternalServerException.class);
    }

    @DisplayName("지원서 폼 제출 시, 개인정보 활용 거부할 경우 예외가 발생한다.")
    @Test
    void submit_rejectPersonalDataCollection() {
        // given
        Dashboard dashboard = dashboardRepository.save(createBackendDashboard());
        processRepository.save(createFirstProcess(dashboard));
        ApplyForm applyForm = applyFormRepository.save(createFrontendApplyForm(dashboard));
        Question question = questionRepository.save(new Question(SHORT_ANSWER, "지원 경로가 어떻게 되나요?", 0, applyForm));

        ApplyFormSubmitRequest request = new ApplyFormSubmitRequest(
                new ApplicantCreateRequest("초코칩", "dev.chocochip@gmail.com", "01000000000"),
                List.of(new AnswerCreateRequest(question.getId(), List.of("온라인"))),
                false);

        // when&then
        assertThatThrownBy(() -> applyFormService.submit(request, applyForm.getId()))
                .isInstanceOf(PersonalDataProcessingException.class);
    }

    @DisplayName("지원서 폼 제출 시, 지원서 폼이 존재하지 않을 경우 예외가 발생한다.")
    @Test
    void submit_invalidApplyForm() {
        // given
        Dashboard dashboard = dashboardRepository.save(createBackendDashboard());
        processRepository.save(createFirstProcess(dashboard));
        ApplyForm applyForm = applyFormRepository.save(createFrontendApplyForm(dashboard));
        Question question = questionRepository.save(new Question(SHORT_ANSWER, "지원 경로가 어떻게 되나요?", 0, applyForm));

        ApplyFormSubmitRequest request = new ApplyFormSubmitRequest(
                new ApplicantCreateRequest("초코칩", "dev.chocochip@gmail.com", "01000000000"),
                List.of(new AnswerCreateRequest(question.getId(), List.of("온라인"))),
                true);

        // when&then
        assertThatThrownBy(() -> applyFormService.submit(request, -1))
                .isInstanceOf(ApplyFormNotFoundException.class);
    }
}
