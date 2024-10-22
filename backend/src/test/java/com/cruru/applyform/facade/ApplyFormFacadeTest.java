package com.cruru.applyform.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.advice.InternalServerException;
import com.cruru.applicant.controller.request.ApplicantCreateRequest;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.controller.request.AnswerCreateRequest;
import com.cruru.applyform.controller.request.ApplyFormSubmitRequest;
import com.cruru.applyform.controller.request.ApplyFormWriteRequest;
import com.cruru.applyform.controller.response.ApplyFormResponse;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.applyform.exception.ApplyFormNotFoundException;
import com.cruru.applyform.exception.badrequest.ApplyFormSubmitOutOfPeriodException;
import com.cruru.applyform.exception.badrequest.PersonalDataCollectDisagreeException;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.controller.response.QuestionResponse;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.AnswerRepository;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.LocalDateFixture;
import com.cruru.util.fixture.ProcessFixture;
import com.cruru.util.fixture.QuestionFixture;
import java.time.LocalDateTime;
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
    private Question question1;
    private List<AnswerCreateRequest> answerCreateRequests;
    private ApplyFormSubmitRequest applyFormSubmitrequest;
    private ApplicantCreateRequest applicantCreateRequest;

    @BeforeEach
    void setUp() {
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend(null));
        firstProcess = processRepository.save(ProcessFixture.applyType(dashboard));
        finalProcess = processRepository.save(ProcessFixture.approveType(dashboard));
        applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        question1 = questionRepository.save(QuestionFixture.longAnswerType(applyForm));
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

    @DisplayName("지원서 폼 제출 시, 지원 날짜 범위 밖이면 예외가 발생한다.")
    @Test
    void submit_invalidSubmitDate() {
        // given
        ApplyForm pastApplyForm = applyFormRepository.save(new ApplyForm(
                "지난 모집 공고", "description",
                LocalDateFixture.oneWeekAgo(), LocalDateFixture.oneDayAgo(), null
        ));
        ApplyForm futureApplyForm = applyFormRepository.save(new ApplyForm(
                "미래의 모집 공고", "description",
                LocalDateFixture.oneDayLater(), LocalDateFixture.oneWeekLater(), null
        ));

        // when&then
        assertAll(
                () -> assertThatThrownBy(() -> applyFormFacade.submit(pastApplyForm.getId(), applyFormSubmitrequest))
                        .isInstanceOf(ApplyFormSubmitOutOfPeriodException.class),
                () -> assertThatThrownBy(() -> applyFormFacade.submit(futureApplyForm.getId(), applyFormSubmitrequest))
                        .isInstanceOf(ApplyFormSubmitOutOfPeriodException.class)
        );
    }

    @DisplayName("지원서 폼 제출 시, 지원서 폼이 존재하지 않을 경우 예외가 발생한다.")
    @Test
    void submit_invalidApplyForm() {
        // given&when&then
        assertThatThrownBy(() -> applyFormFacade.submit(-1L, applyFormSubmitrequest))
                .isInstanceOf(ApplyFormNotFoundException.class);
    }

    @DisplayName("지원서 폼을 수정한다.")
    @Test
    void update() {
        // given
        String toChangeTitle = "크루루 백엔드 모집 공고~~";
        String toChangeDescription = "# 모집 공고 설명 #";
        LocalDateTime toChangeStartDate = LocalDateFixture.oneDayLater();
        LocalDateTime toChangeEndDate = LocalDateFixture.oneWeekLater();

        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        ApplyFormWriteRequest request = new ApplyFormWriteRequest(
                toChangeTitle, toChangeDescription, toChangeStartDate, toChangeEndDate
        );

        // when
        applyFormFacade.update(request, applyForm.getId());

        // then
        ApplyForm actual = applyFormRepository.findById(applyForm.getId()).get();
        assertAll(
                () -> assertThat(actual.getTitle()).isEqualTo(toChangeTitle),
                () -> assertThat(actual.getDescription()).isEqualTo(toChangeDescription),
                () -> assertThat(actual.getStartDate()).isEqualTo(toChangeStartDate),
                () -> assertThat(actual.getEndDate()).isEqualTo(toChangeEndDate)
        );
    }

    @DisplayName("지원서 폼 조회에 성공한다.")
    @Test
    void readApplyFormById() {
        // given&when
        ApplyFormResponse applyFormResponse = applyFormFacade.readApplyFormById(applyForm.getId());

        // then
        assertAll(
                () -> assertThat(applyFormResponse.title()).isEqualTo(applyForm.getTitle()),
                () -> assertThat(applyFormResponse.startDate()).isEqualTo(applyForm.getStartDate()),
                () -> assertThat(applyFormResponse.endDate()).isEqualTo(applyForm.getEndDate()),
                () -> {
                    QuestionResponse questionResponse = applyFormResponse.questionResponses().get(0);
                    assertThat(questionResponse.id()).isEqualTo(question1.getId());
                    assertThat(questionResponse.content()).isEqualTo(question1.getContent());
                    assertThat(questionResponse.orderIndex()).isEqualTo(question1.getSequence());
                    assertThat(questionResponse.required()).isEqualTo(question1.isRequired());
                    assertThat(questionResponse.choiceResponses()).isEmpty();
                }
        );
    }
}
