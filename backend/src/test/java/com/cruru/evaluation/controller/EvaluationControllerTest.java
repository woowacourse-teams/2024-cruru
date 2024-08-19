package com.cruru.evaluation.controller;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.evaluation.controller.dto.EvaluationCreateRequest;
import com.cruru.evaluation.controller.dto.EvaluationUpdateRequest;
import com.cruru.evaluation.domain.Evaluation;
import com.cruru.evaluation.domain.repository.EvaluationRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ControllerTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.EvaluationFixture;
import com.cruru.util.fixture.ProcessFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("평가 컨트롤러 테스트")
class EvaluationControllerTest extends ControllerTest {

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    private Process process;

    private Applicant applicant;

    @BeforeEach
    void setUp() {
        process = processRepository.save(ProcessFixture.first());

        applicant = applicantRepository.save(ApplicantFixture.pendingDobby(process));
    }

    @DisplayName("평가 생성 성공 시, 201을 응답한다.")
    @Test
    void create() {
        // given
        int score = 4;
        String content = "서류가 인상적입니다.";
        String url = String.format("/v1/evaluations?processId=%d&applicantId=%d", process.getId(), applicant.getId());
        EvaluationCreateRequest request = new EvaluationCreateRequest(score, content);

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post(url)
                .then().log().all().statusCode(201);
    }

    @DisplayName("지원자가 존재하지 않을 경우, 404를 응답한다.")
    @Test
    void create_applicantNotFound() {
        // given
        int score = 4;
        String content = "서류가 인상적입니다.";
        long invalidApplicantId = -1;
        String url = String.format(
                "/v1/evaluations?processId=%d&applicantId=%d",
                process.getId(),
                invalidApplicantId
        );
        EvaluationCreateRequest request = new EvaluationCreateRequest(score, content);

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post(url)
                .then().log().all().statusCode(404);
    }

    @DisplayName("프로세스가 존재하지 않을 경우, 404를 응답한다.")
    @Test
    void create_processNotFound() {
        // given
        int score = 4;
        String content = "서류가 인상적입니다.";
        long invalidProcessId = -1;
        String url = String.format(
                "/v1/evaluations?processId=%d&applicantId=%d",
                invalidProcessId,
                applicant.getId()
        );
        EvaluationCreateRequest request = new EvaluationCreateRequest(score, content);

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post(url)
                .then().log().all().statusCode(404);
    }

    @DisplayName("평가 조회에 성공할 경우, 200을 응답한다.")
    @Test
    void read() {
        // given
        String url = String.format("/v1/evaluations?processId=%d&applicantId=%d", process.getId(), applicant.getId());

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get(url)
                .then().log().all().statusCode(200);
    }

    @DisplayName("평가 수정에 성공할 경우, 200을 응답한다.")
    @Test
    void update() {
        // given
        Evaluation evaluation = evaluationRepository.save(EvaluationFixture.fivePoints());
        int score = 2;
        String content = "맞춤법이 틀렸습니다.";
        EvaluationUpdateRequest request = new EvaluationUpdateRequest(score, content);

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().patch("/v1/evaluations/{evaluationId}", evaluation.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("평가 수정시 평가가 존재하지 않을 경우, 404를 응답한다.")
    @Test
    void update_evaluationNotFound() {
        // given
        int score = 2;
        String content = "맞춤법이 틀렸습니다.";
        EvaluationUpdateRequest request = new EvaluationUpdateRequest(score, content);

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().patch("/v1/evaluations/{evaluationId}", -1)
                .then().log().all().statusCode(404);
    }
}
