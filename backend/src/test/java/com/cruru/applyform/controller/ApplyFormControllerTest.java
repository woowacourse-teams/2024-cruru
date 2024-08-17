package com.cruru.applyform.controller;

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
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.ProcessFixture;
import com.cruru.util.fixture.QuestionFixture;
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
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        processRepository.save(ProcessFixture.first(dashboard));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.frontend(dashboard));
        Question question1 = questionRepository.save(QuestionFixture.shortAnswerType(applyForm));
        Question question2 = questionRepository.save(QuestionFixture.longAnswerType(applyForm));

        List<AnswerCreateRequest> answerCreateRequests = List.of(
                new AnswerCreateRequest(question1.getId(), List.of("안녕하세요, 맛있는 초코칩입니다.")),
                new AnswerCreateRequest(question2.getId(), List.of("온라인"))
        );
        ApplyFormSubmitRequest request = new ApplyFormSubmitRequest(
                new ApplicantCreateRequest("초코칩", "dev.chocochip@gmail.com", "01000000000"),
                answerCreateRequests,
                true
        );

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/v1/applyform/{applyFormId}/submit", applyForm.getId())
                .then().log().all().statusCode(201);
    }

    @DisplayName("지원서 폼 제출 시, 개인정보 활용을 거부할 경우 400을 반환한다.")
    @Test
    void submit_rejectPersonalDataCollection() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        processRepository.save(ProcessFixture.first(dashboard));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.frontend(dashboard));
        Question question1 = questionRepository.save(QuestionFixture.shortAnswerType(applyForm));
        Question question2 = questionRepository.save(QuestionFixture.longAnswerType(applyForm));

        List<AnswerCreateRequest> answerCreateRequests = List.of(
                new AnswerCreateRequest(question1.getId(), List.of("안녕하세요, 맛있는 초코칩입니다.")),
                new AnswerCreateRequest(question2.getId(), List.of("온라인"))
        );
        ApplyFormSubmitRequest request = new ApplyFormSubmitRequest(
                new ApplicantCreateRequest("초코칩", "dev.chocochip@gmail.com", "01000000000"),
                answerCreateRequests,
                false
        );

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/v1/applyform/{applyFormId}/submit", applyForm.getId())
                .then().log().all().statusCode(400);
    }

    @DisplayName("지원서 폼 제출 시, 대시보드에 프로세스가 존재하지 않으면 500을 반환한다.")
    @Test
    void submit_dashboardWithNoProcess() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.frontend(dashboard));
        Question question = questionRepository.save(QuestionFixture.shortAnswerType(applyForm));

        ApplyFormSubmitRequest request = new ApplyFormSubmitRequest(
                new ApplicantCreateRequest("초코칩", "dev.chocochip@gmail.com", "01000000000"),
                List.of(new AnswerCreateRequest(question.getId(), List.of("온라인"))),
                true
        );

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/v1/applyform/{applyFormId}/submit", applyForm.getId())
                .then().log().all().statusCode(500);
    }

    @DisplayName("지원서 폼 조회 시, 200을 반환한다.")
    @Test
    void read() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        processRepository.save(ProcessFixture.first(dashboard));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        questionRepository.save(QuestionFixture.shortAnswerType(applyForm));

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/v1/applyform/{applyFormId}", applyForm.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("지원서 폼 조회 시, 지원서 폼이 존재하지 않을 경우 404을 반환한다.")
    @Test
    void read_notFound() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        processRepository.save(ProcessFixture.first(dashboard));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        questionRepository.save(QuestionFixture.shortAnswerType(applyForm));

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/v1/applyform/{applyFormId}", -1)
                .then().log().all().statusCode(404);
    }
}
