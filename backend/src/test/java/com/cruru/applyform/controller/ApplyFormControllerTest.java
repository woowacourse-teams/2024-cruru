package com.cruru.applyform.controller;

import static com.cruru.question.domain.QuestionType.SHORT_ANSWER;
import static com.cruru.util.fixture.ApplyFormFixture.createFrontendApplyForm;
import static com.cruru.util.fixture.DashboardFixture.createBackendDashboard;
import static com.cruru.util.fixture.ProcessFixture.createFirstProcess;

import com.cruru.applicant.controller.dto.ApplicantCreateRequest;
import com.cruru.applyform.controller.dto.AnswerCreateRequest;
import com.cruru.applyform.controller.dto.ApplyFormSubmitRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ControllerTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("지원서 폼 컨트롤러 테스트")
class ApplyFormControllerTest extends ControllerTest {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @DisplayName("지원서 폼 제출 시, 201을 반환한다.")
    @Test
    void submit() {
        // given
        Dashboard dashboard = dashboardRepository.save(createBackendDashboard());
        processRepository.save(createFirstProcess(dashboard));
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

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/v1/applyform/" + applyForm.getId() + "/submit")
                .then().log().all().statusCode(201);
    }

    @DisplayName("지원서 폼 제출 시, 개인정보 활용을 거부할 경우 400을 반환한다.")
    @Test
    void submit_rejectPersonalDataCollection() {
        // given
        Dashboard dashboard = dashboardRepository.save(createBackendDashboard());
        processRepository.save(createFirstProcess(dashboard));
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
                false);

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/v1/applyform/" + applyForm.getId() + "/submit")
                .then().log().all().statusCode(400);
    }

    @DisplayName("지원서 폼 제출 시, 대시보드에 프로세스가 존재하지 않으면 500을 반환한다.")
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
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/v1/applyform/" + applyForm.getId() + "/submit")
                .then().log().all().statusCode(500);
    }
}
