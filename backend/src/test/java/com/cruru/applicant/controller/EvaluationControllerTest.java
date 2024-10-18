package com.cruru.applicant.controller;

import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.cruru.applicant.controller.request.EvaluationCreateRequest;
import com.cruru.applicant.controller.request.EvaluationUpdateRequest;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.Evaluation;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applicant.domain.repository.EvaluationRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ControllerTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.EvaluationFixture;
import com.cruru.util.fixture.ProcessFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.FieldDescriptor;

@DisplayName("평가 컨트롤러 테스트")
class EvaluationControllerTest extends ControllerTest {

    private static final FieldDescriptor[] EVALUATION_FIELD_DESCRIPTORS = {
            fieldWithPath("evaluationId").description("평가의 id"),
            fieldWithPath("score").description("평가 점수"),
            fieldWithPath("content").description("평가 내용"),
            fieldWithPath("createdDate").description("평가 생성 날짜")
    };

    @Autowired
    private DashboardRepository dashboardRepository;

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
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend(defaultClub));
        process = processRepository.save(ProcessFixture.applyType(dashboard));
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
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document(
                        "evaluation/create",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(
                                parameterWithName("processId").description("프로세스의 id"),
                                parameterWithName("applicantId").description("지원자의 id")
                        ),
                        requestFields(
                                fieldWithPath("score").description("평가 점수"),
                                fieldWithPath("content").description("평가 주관식 내용")
                        )
                ))
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
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document(
                        "evaluation/create-fail/applicant-not-found",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(
                                parameterWithName("processId").description("프로세스의 id"),
                                parameterWithName("applicantId").description("존재하지 않는 지원자의 id")
                        ),
                        requestFields(
                                fieldWithPath("score").description("평가 점수"),
                                fieldWithPath("content").description("평가 주관식 내용")
                        )
                ))
                .when().post(url)
                .then().log().all().statusCode(404);
    }

    @DisplayName("프로세스가 존재하지 않을 경우, 404를 응답한다.")
    @Test
    void create_processNotFound() {
        // given
        int score = 4;
        String content = "서류가 인상적입니다.";
        Long invalidProcessId = -1L;
        String url = String.format(
                "/v1/evaluations?processId=%d&applicantId=%d",
                invalidProcessId,
                applicant.getId()
        );
        EvaluationCreateRequest request = new EvaluationCreateRequest(score, content);

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document(
                        "evaluation/create-fail/process-not-found",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(
                                parameterWithName("processId").description("존재하지 않는 프로세스의 id"),
                                parameterWithName("applicantId").description("지원자의 id")
                        ),
                        requestFields(
                                fieldWithPath("score").description("평가 점수"),
                                fieldWithPath("content").description("평가 주관식 내용")
                        )
                ))
                .when().post(url)
                .then().log().all().statusCode(404);
    }

    @DisplayName("유효하지 않는 점수일 경우, 400을 응답한다.")
    @Test
    void create_invalidScore() {
        // given
        int invalidScore = -4;
        String content = "서류가 인상적입니다.";
        String url = String.format(
                "/v1/evaluations?processId=%d&applicantId=%d",
                process.getId(),
                applicant.getId()
        );
        EvaluationCreateRequest request = new EvaluationCreateRequest(invalidScore, content);

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document(
                        "evaluation/create-fail/invalid-score",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(
                                parameterWithName("processId").description("프로세스의 id"),
                                parameterWithName("applicantId").description("지원자의 id")
                        ),
                        requestFields(
                                fieldWithPath("score").description("적절하지 않은 평가 점수"),
                                fieldWithPath("content").description("평가 주관식 내용")
                        )
                ))
                .when().post(url)
                .then().log().all().statusCode(400);
    }

    @DisplayName("평가 조회에 성공할 경우, 200을 응답한다.")
    @Test
    void read() {
        // given
        evaluationRepository.save(EvaluationFixture.fivePoints(process, applicant));
        String url = String.format("/v1/evaluations?processId=%d&applicantId=%d", process.getId(), applicant.getId());

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .filter(document(
                        "evaluation/read",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(
                                parameterWithName("processId").description("프로세스의 id"),
                                parameterWithName("applicantId").description("지원자의 id")
                        ),
                        responseFields(
                                fieldWithPath("evaluations").description("평가 목록")
                        ).andWithPrefix("evaluations[].", EVALUATION_FIELD_DESCRIPTORS)
                ))
                .when().get(url)
                .then().log().all().statusCode(200);
    }

    @DisplayName("지원자가 존재하지 않을 경우, 404를 응답한다.")
    @Test
    void read_applicantNotFound() {
        // given
        long invalidApplicantId = -1;
        String url = String.format(
                "/v1/evaluations?processId=%d&applicantId=%d",
                process.getId(),
                invalidApplicantId
        );

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .filter(document(
                        "evaluation/read-fail/applicant-not-found",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(
                                parameterWithName("processId").description("프로세스의 id"),
                                parameterWithName("applicantId").description("지원자의 id")
                        )
                ))
                .when().get(url)
                .then().log().all().statusCode(404);
    }

    @DisplayName("프로세스가 존재하지 않을 경우, 404를 응답한다.")
    @Test
    void read_processNotFound() {
        // given
        long invalidProcessId = -1;
        String url = String.format(
                "/v1/evaluations?processId=%d&applicantId=%d",
                invalidProcessId,
                applicant.getId()
        );

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .filter(document(
                        "evaluation/read-fail/process-not-found",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(
                                parameterWithName("processId").description("프로세스의 id"),
                                parameterWithName("applicantId").description("지원자의 id")
                        )
                ))
                .when().get(url)
                .then().log().all().statusCode(404);
    }

    @DisplayName("평가 수정에 성공할 경우, 200을 응답한다.")
    @Test
    void update() {
        // given
        Evaluation evaluation = evaluationRepository.save(EvaluationFixture.fivePoints(process, applicant));
        int score = 2;
        String content = "맞춤법이 틀렸습니다.";
        EvaluationUpdateRequest request = new EvaluationUpdateRequest(score, content);

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document(
                        "evaluation/update",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("evaluationId").description("평가의 id")),
                        requestFields(
                                fieldWithPath("score").description("평가 점수"),
                                fieldWithPath("content").description("평가 주관식 내용")
                        )
                ))
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
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document(
                        "evaluation/update-fail/evaluation-not-found",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("evaluationId").description("존재하지 않는 평가의 id")),
                        requestFields(
                                fieldWithPath("score").description("평가 점수"),
                                fieldWithPath("content").description("평가 주관식 내용")
                        )
                ))
                .when().patch("/v1/evaluations/{evaluationId}", -1)
                .then().log().all().statusCode(404);
    }

    @DisplayName("평가 수정시 평가가 유효하지 않은 점수일 경우, 400를 응답한다.")
    @Test
    void update_invalidScore() {
        // given
        Evaluation evaluation = evaluationRepository.save(EvaluationFixture.fivePoints());
        int score = -1;
        String content = "맞춤법이 틀렸습니다.";
        EvaluationUpdateRequest request = new EvaluationUpdateRequest(score, content);

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document(
                        "evaluation/update-fail/invalid-score",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("evaluationId").description("평가의 id")),
                        requestFields(
                                fieldWithPath("score").description("적절하지 않은 평가 점수"),
                                fieldWithPath("content").description("평가 주관식 내용")
                        )
                ))
                .when().patch("/v1/evaluations/{evaluationId}", evaluation.getId())
                .then().log().all().statusCode(400);
    }
}
