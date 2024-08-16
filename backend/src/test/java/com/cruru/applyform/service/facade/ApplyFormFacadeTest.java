package com.cruru.applyform.service.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.advice.InternalServerException;
import com.cruru.answer.domain.repository.AnswerRepository;
import com.cruru.applicant.controller.dto.ApplicantCreateRequest;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.controller.dto.AnswerCreateRequest;
import com.cruru.applyform.controller.dto.ApplyFormSubmitRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.applyform.exception.ApplyFormNotFoundException;
import com.cruru.applyform.exception.badrequest.PersonalDataCollectDisagreeException;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.ProcessFixture;
import com.cruru.util.fixture.QuestionFixture;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("지원서폼 파사드 서비스 테스트")
class ApplyFormFacadeTest extends ServiceTest {

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
    private ApplyFormFacade applyFormFacade;

    private Process firstProcess;
    private Process finalProcess;
    private ApplyForm applyForm;
    private List<AnswerCreateRequest> answerCreateRequests;
    private ApplyFormSubmitRequest applyFormSubmitrequest;
    private ApplicantCreateRequest applicantCreateRequest;

    @BeforeEach
    void setUp() {
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend(null));
        firstProcess = processRepository.save(ProcessFixture.first(dashboard));
        finalProcess = processRepository.save(ProcessFixture.last(dashboard));
        applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        Question question1 = questionRepository.save(QuestionFixture.longAnswerType(applyForm));
        Question question2 = questionRepository.save(QuestionFixture.shortAnswerType(applyForm));

        answerCreateRequests = List.of(
                new AnswerCreateRequest(question1.getId(), List.of("안녕하세요, 첫 번째 답변입니다")),
                new AnswerCreateRequest(question2.getId(), List.of("온라인"))
        );

        applicantCreateRequest = new ApplicantCreateRequest(
                "초코칩",
                "dev.chocochip@gmail.com",
                "01000000000"
        );

        applyFormSubmitrequest = new ApplyFormSubmitRequest(applicantCreateRequest, answerCreateRequests, true);
    }

    @DisplayName("지원서 폼 제출에 성공한다.")
    @Test
    void submit() {
        // given&when
        applyFormFacade.submit(applyForm.getId(), applyFormSubmitrequest);

        // then
        assertAll(
                () -> assertThat(answerRepository.findAll()).hasSize(answerCreateRequests.size()),
                () -> assertThat(applicantRepository.countByProcess(firstProcess)).isEqualTo(1),
                () -> assertThat(applicantRepository.countByProcess(finalProcess)).isZero()
        );
    }

    @DisplayName("지원서 폼 제출 시, 대시보드에 프로세스가 존재하지 않으면 예외가 발생한다.")
    @Test
    void submit_dashboardWithNoProcess() {
        // given
        Dashboard emptyProcessDashboard = dashboardRepository.save(DashboardFixture.backend(null));
        ApplyForm emptyProcessApplyForm = applyFormRepository.save(ApplyFormFixture.backend(emptyProcessDashboard));

        // when&then
        Long emptyProcessApplyFormId = emptyProcessApplyForm.getId();
        assertThatThrownBy(() -> applyFormFacade.submit(emptyProcessApplyFormId, applyFormSubmitrequest))
                .isInstanceOf(InternalServerException.class);
    }

    @DisplayName("지원서 폼 제출 시, 개인정보수집에 동의하지 않은 요청은 예외가 발생한다.")
    @Test
    void submit_rejectPersonalDataCollection() {
        // given
        ApplyFormSubmitRequest notAgreedRequest = new ApplyFormSubmitRequest(
                applicantCreateRequest,
                answerCreateRequests,
                false
        );

        // when&then
        Long applyFormId = applyForm.getId();
        assertThatThrownBy(() -> applyFormFacade.submit(applyFormId, notAgreedRequest))
                .isInstanceOf(PersonalDataCollectDisagreeException.class);
    }

    @DisplayName("지원서 폼 제출 시, 지원서 폼이 존재하지 않을 경우 예외가 발생한다.")
    @Test
    void submit_invalidApplyForm() {
        // given&when&then
        assertThatThrownBy(() -> applyFormFacade.submit(-1, applyFormSubmitrequest))
                .isInstanceOf(ApplyFormNotFoundException.class);
    }
}
