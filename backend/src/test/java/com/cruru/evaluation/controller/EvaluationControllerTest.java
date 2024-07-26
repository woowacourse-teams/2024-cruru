package com.cruru.evaluation.controller;

import static com.cruru.util.fixture.ApplicantFixture.createApplicantDobby;
import static com.cruru.util.fixture.ProcessFixture.createFirstProcess;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.evaluation.controller.dto.EvaluationCreateRequest;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ControllerTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.payload.FieldDescriptor;

@DisplayName("평가 컨트롤러 테스트")
class EvaluationControllerTest extends ControllerTest {

    private static final FieldDescriptor[] EVALUATION_DESCRIPTOR = {
            fieldWithPath("score").description("평가 점수"),
            fieldWithPath("content").description("평가 내용")
    };
    @LocalServerPort
    private int port;
    @Autowired
    private ProcessRepository processRepository;
    @Autowired
    private ApplicantRepository applicantRepository;
    private Process process;
    private Applicant applicant;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        process = processRepository.save(createFirstProcess());

        applicant = applicantRepository.save(createApplicantDobby(process));
    }

    @DisplayName("평가 생성 성공 시, 201을 응답한다.")
    @Test
    void create() {
        // given
        int score = 4;
        String content = "great";
        String url = String.format("/v1/evaluations?process_id=%d&applicant_id=%d", process.getId(), applicant.getId());
        EvaluationCreateRequest request = new EvaluationCreateRequest(score, content);

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("evaluation/create/",
                        queryParameters(
                                parameterWithName("process_id").description("평가가 해당되는 프로세스의 id"),
                                parameterWithName("applicant_id").description("평가 대상 지원자의 id")
                        ),
                        requestFields(EVALUATION_DESCRIPTOR)))
                .when().post(url)
                .then().log().all().statusCode(201);
    }

    @DisplayName("지원자가 존재하지 않을 경우, 404를 응답한다.")
    @Test
    void create_applicantNotFound() {
        // given
        int score = 4;
        String content = "great";
        long invalidApplicantId = -1;
        String url = String.format(
                "/v1/evaluations?process_id=%d&applicant_id=%d",
                process.getId(),
                invalidApplicantId
        );
        EvaluationCreateRequest request = new EvaluationCreateRequest(score, content);

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("evaluation/create-fail/applicant-not-found/",
                        queryParameters(
                                parameterWithName("process_id").description("평가가 해당되는 프로세스의 id"),
                                parameterWithName("applicant_id").description("존재하지 않는 지원자의 id")
                        ),
                        requestFields(EVALUATION_DESCRIPTOR)))
                .when().post(url)
                .then().log().all().statusCode(404);
    }

    @DisplayName("프로세스가 존재하지 않을 경우, 404를 응답한다.")
    @Test
    void create_processNotFound() {
        // given
        int score = 4;
        String content = "great";
        long invalidProcessId = -1;
        String url = String.format(
                "/v1/evaluations?process_id=%d&applicant_id=%d",
                invalidProcessId,
                applicant.getId()
        );
        EvaluationCreateRequest request = new EvaluationCreateRequest(score, content);

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("evaluation/create-fail/process-not-found/",
                        queryParameters(
                                parameterWithName("process_id").description("존재하지 않는 프로세스의 id"),
                                parameterWithName("applicant_id").description("평가 대상 지원자의 id")
                        ),
                        requestFields(EVALUATION_DESCRIPTOR)))
                .when().post(url)
                .then().log().all().statusCode(404);
    }

    @DisplayName("범위가 벗어난 점수의 평가 생성 시, 400을 응답한다.")
    @Test
    void create_invalidScore() {
        // given
        int invalidScore = 6;
        String content = "great";
        String url = String.format("/v1/evaluations?process_id=%d&applicant_id=%d", process.getId(), applicant.getId());
        EvaluationCreateRequest request = new EvaluationCreateRequest(invalidScore, content);

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("evaluation/create-fail/invalid-score/",
                        queryParameters(
                                parameterWithName("process_id").description("평가가 해당되는 프로세스의 id"),
                                parameterWithName("applicant_id").description("평가 대상 지원자의 id")
                        ),
                        requestFields(
                                fieldWithPath("score").description("범위를 벗어난 평가 점수"),
                                fieldWithPath("content").description("평가 내용")
                        )
                ))
                .when().post(url)
                .then().log().all().statusCode(400);
    }

    @DisplayName("평가 조회에 성공할 경우, 200을 응답한다.")
    @Test
    void read() {
        // given
        EvaluationCreateRequest request = new EvaluationCreateRequest(4, "서류가 인상적입니다.");
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post(String.format(
                        "/v1/evaluations?process_id=%d&applicant_id=%d", process.getId(), applicant.getId()))
                .then().log().all().statusCode(201);

        String url = String.format("/v1/evaluations?process_id=%d&applicant_id=%d", process.getId(), applicant.getId());
        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .filter(document("evaluation/read/",
                        queryParameters(
                                parameterWithName("process_id").description("평가가 해당되는 프로세스의 id"),
                                parameterWithName("applicant_id").description("평가 대상 지원자의 id")
                        ),
                        responseFields(fieldWithPath("evaluations").description("평가 목록"))
                                .andWithPrefix("evaluations[].",
                                        fieldWithPath("evaluation_id").description("평가 id"),
                                        fieldWithPath("score").description("평가 점수"),
                                        fieldWithPath("content").description("평가 내용"))
                ))
                .when().get(url)
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 지원자의 평가 조회를 시도한 경우, 4044를 응답한다.")
    @Test
    void read_applicantNotFound() {
        // given
        long invalidApplicantId = -1;
        String url = String.format("/v1/evaluations?process_id=%d&applicant_id=%d", process.getId(),
                invalidApplicantId);

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .filter(document("evaluation/read-fail/applicant-not-found/",
                        queryParameters(
                                parameterWithName("process_id").description("평가가 해당되는 프로세스의 id"),
                                parameterWithName("applicant_id").description("존재하지 않는 지원자의 id")
                        )
                ))
                .when().get(url)
                .then().log().all().statusCode(404);
    }

    @DisplayName("존재하지 않는 프로세스의 평가 조회를 시도한 경우, 404를 응답한다.")
    @Test
    void read_processNotFound() {
        // given
        long invalidProcessId = -1;
        String url = String.format("/v1/evaluations?process_id=%d&applicant_id=%d", invalidProcessId,
                applicant.getId());

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .filter(document("evaluation/read-fail/process-not-found/",
                        queryParameters(
                                parameterWithName("process_id").description("존재하지 않는 프로세스의 id"),
                                parameterWithName("applicant_id").description("평가 대상 지원자의 id")
                        )
                ))
                .when().get(url)
                .then().log().all().statusCode(404);
    }
}
