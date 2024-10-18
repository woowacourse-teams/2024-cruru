package com.cruru.process.controller;

import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.controller.request.ProcessCreateRequest;
import com.cruru.process.controller.request.ProcessUpdateRequest;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ControllerTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.DefaultFilterAndOrderFixture;
import com.cruru.util.fixture.ProcessFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.FieldDescriptor;

@DisplayName("프로세스 컨트롤러 테스트")
class ProcessControllerTest extends ControllerTest {

    private static final FieldDescriptor[] PROCESS_RESPONSE_FIELD_DESCRIPTORS = {
            fieldWithPath("processId").description("프로세스의 id"),
            fieldWithPath("orderIndex").description("프로세스의 순서"),
            fieldWithPath("name").description("프로세스명"),
            fieldWithPath("description").description("프로세스의 설명"),
            fieldWithPath("applicants").description("프로세스에 속한 지원자들")
    };

    private static final FieldDescriptor[] APPLICANT_RESPONSE_FIELD_DESCRIPTORS = {
            fieldWithPath("applicantId").description("지원자의 id"),
            fieldWithPath("applicantName").description("지원자의 이름"),
            fieldWithPath("createdAt").description("지원자의 지원날짜"),
            fieldWithPath("isRejected").description("지원자의 불합격 여부"),
            fieldWithPath("evaluationCount").description("지원자의 평가 개수"),
            fieldWithPath("averageScore").description("지원자의 평가 평균 점수"),
            };

    private static final FieldDescriptor[] PROCESS_CREATE_FIELD_DESCRIPTORS = {
            fieldWithPath("processName").description("프로세스명"),
            fieldWithPath("description").description("프로세스의 설명"),
            fieldWithPath("orderIndex").description("프로세스의 순서")
    };

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    private Dashboard dashboard;

    @BeforeEach
    void setUp() {
        dashboard = dashboardRepository.save(DashboardFixture.backend(defaultClub));
        applyFormRepository.save(ApplyFormFixture.backend(dashboard));
    }

    @DisplayName("프로세스 조회 성공 시, 200을 응답한다.")
    @Test
    void read() {
        // given
        List<Process> processes = processRepository.saveAll(List.of(
                ProcessFixture.applyType(dashboard),
                ProcessFixture.interview(dashboard),
                ProcessFixture.applyType(dashboard)
        ));
        applicantRepository.save(ApplicantFixture.pendingDobby(processes.get(0)));
        String url = String.format("/v1/processes?dashboardId=%d", dashboard.getId());

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .filter(document(
                        "process/read",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(parameterWithName("dashboardId").description("대시보드의 id")),
                        responseFields(
                                fieldWithPath("applyFormId").description("지원폼의 id"),
                                fieldWithPath("processes").description("프로세스 목록"),
                                fieldWithPath("title").description("지원폼의 제목"),
                                fieldWithPath("startDate").description("지원폼 모집 시작일"),
                                fieldWithPath("endDate").description("지원폼 모집 종료일")
                        ).andWithPrefix("processes[].", PROCESS_RESPONSE_FIELD_DESCRIPTORS)
                                .andWithPrefix("processes[].applicants[]", APPLICANT_RESPONSE_FIELD_DESCRIPTORS)
                ))
                .when().get(url)
                .then().log().all().statusCode(200);
    }

    @DisplayName("프로세스 목록 조회 성공 시, 200을 응답한다.")
    @Test
    void read_filterAndOrder() {
        // given
        List<Process> processes = processRepository.saveAll(List.of(
                ProcessFixture.applyType(dashboard),
                ProcessFixture.interview(dashboard),
                ProcessFixture.applyType(dashboard)
        ));
        applicantRepository.save(ApplicantFixture.pendingDobby(processes.get(0)));
        String sortByCreatedAt = "DESC";
        String sortByScore = null;
        String url = String.format("/v1/processes?dashboardId=%d&minScore=%.2f&maxScore=%.2f"
                        + "&evaluationStatus=%s&sortByCreatedAt=%s&sortByScore=%s",
                dashboard.getId(),
                DefaultFilterAndOrderFixture.DEFAULT_MIN_SCORE,
                DefaultFilterAndOrderFixture.DEFAULT_MAX_SCORE,
                DefaultFilterAndOrderFixture.DEFAULT_EVALUATION_STATUS,
                sortByCreatedAt,
                sortByScore
        );

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .filter(document(
                        "process/read-filter-and-order",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(
                                parameterWithName("dashboardId").description("대시보드의 id, required=true"),
                                parameterWithName("minScore").description("지원자 최소 평균 점수: 0.00(default) ~ 5.00, required=false").optional(),
                                parameterWithName("maxScore").description("지원자 최대 평균 점수: 0.00 ~ 5.00(default), required=false").optional(),
                                parameterWithName("evaluationStatus").description(
                                        "지원자 평가 유무: ALL(default), NOT_EVALUATED, EVALUATED, required=false").optional(),
                                parameterWithName("sortByCreatedAt").description("지원자 지원 날짜 정렬 조건: DESC, ASC, required=false").optional(),
                                parameterWithName("sortByScore").description("지원자 평균 점수 정렬 조건: DESC, ASC, required=false").optional()
                        ),
                        responseFields(
                                fieldWithPath("applyFormId").description("지원폼의 id"),
                                fieldWithPath("processes").description("프로세스 목록"),
                                fieldWithPath("title").description("지원폼의 제목"),
                                fieldWithPath("startDate").description("지원폼 모집 시작일"),
                                fieldWithPath("endDate").description("지원폼 모집 종료일")
                        ).andWithPrefix("processes[].", PROCESS_RESPONSE_FIELD_DESCRIPTORS)
                                .andWithPrefix("processes[].applicants[]", APPLICANT_RESPONSE_FIELD_DESCRIPTORS)
                ))
                .when().get(url)
                .then().log().all().statusCode(200);
    }

    @DisplayName("프로세스 조회 실패 시, 404를 응답한다.")
    @Test
    void read_dashboardNotFound() {
        // given
        long invalidDashboardId = 0;
        String url = String.format("/v1/processes?dashboardId=%d", invalidDashboardId);

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .filter(document(
                        "process/read-fail/dashboard-not-found",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(parameterWithName("dashboardId").description("존재하지 않는 대시보드의 id"))
                ))
                .when().get(url)
                .then().log().all().statusCode(404);
    }

    @DisplayName("프로세스 생성 성공 시, 201을 응답한다.")
    @Test
    void create() {
        // given
        ProcessCreateRequest processCreateRequest = new ProcessCreateRequest("1차 면접", "화상 면접", 1);
        String url = String.format("/v1/processes?dashboardId=%d", dashboard.getId());

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(processCreateRequest)
                .filter(document(
                        "process/create",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(parameterWithName("dashboardId").description("대시보드의 id")),
                        requestFields(PROCESS_CREATE_FIELD_DESCRIPTORS)
                ))
                .when().post(url)
                .then().log().all().statusCode(201);
    }

    @DisplayName("프로세스 생성 시 대시보드가 존재하지 않을 경우, 404를 응답한다.")
    @Test
    void create_dashboardNotFound() {
        // given
        ProcessCreateRequest processCreateRequest = new ProcessCreateRequest("1차 면접", "화상 면접", 1);
        String url = String.format("/v1/processes?dashboardId=%d", -1);

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(processCreateRequest)
                .filter(document(
                        "process/create-fail/dashboard-not-found",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(parameterWithName("dashboardId").description("대시보드의 id")),
                        requestFields(PROCESS_CREATE_FIELD_DESCRIPTORS)
                ))
                .when().post(url)
                .then().log().all().statusCode(404);
    }

    @DisplayName("프로세스 생성 시 이름이 유효하지 않을 경우, 400를 응답한다.")
    @Test
    void create_invalidName() {
        // given
        ProcessCreateRequest processCreateRequest = new ProcessCreateRequest("", "화상 면접", 1);
        String url = String.format("/v1/processes?dashboardId=%d", dashboard.getId());

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(processCreateRequest)
                .filter(document(
                        "process/create-fail/invalid-name",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(parameterWithName("dashboardId").description("대시보드의 id")),
                        requestFields(
                                fieldWithPath("processName").description("부적절한 프로세스명"),
                                fieldWithPath("description").description("프로세스의 설명"),
                                fieldWithPath("orderIndex").description("프로세스의 순서")
                        )
                ))
                .when().post(url)
                .then().log().all().statusCode(400);
    }

    @DisplayName("프로세스가 최대일 때 생성 시도 시, 400을 응답한다.")
    @Test
    void create_processCountOvered() {
        // given
        processRepository.saveAll(List.of(
                ProcessFixture.applyType(dashboard),
                ProcessFixture.interview(dashboard),
                ProcessFixture.interview(dashboard),
                ProcessFixture.interview(dashboard),
                ProcessFixture.applyType(dashboard)
        ));

        ProcessCreateRequest processCreateRequest = new ProcessCreateRequest("name", "description", 3);
        String url = String.format("/v1/processes?dashboardId=%d", dashboard.getId());

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(processCreateRequest)
                .filter(document(
                        "process/create-fail/process-count-overed/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(parameterWithName("dashboardId").description("생성할 프로세스의 대시보드 id")),
                        requestFields(PROCESS_CREATE_FIELD_DESCRIPTORS)
                ))
                .when().post(url)
                .then().log().all().statusCode(400);
    }

    @DisplayName("존재하는 프로세스의 이름과 설명 변경 성공시, 200을 응답한다.")
    @Test
    void update() {
        // given
        Process process = processRepository.save(ProcessFixture.applyType(dashboard));
        applicantRepository.save(ApplicantFixture.pendingDobby(process));
        ProcessUpdateRequest processUpdateRequest = new ProcessUpdateRequest("임시 과정", "수정된 프로세스");

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(processUpdateRequest)
                .filter(document(
                        "process/update",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("processId").description("수정될 프로세스의 id")),
                        requestFields(
                                fieldWithPath("processName").description("프로세스명"),
                                fieldWithPath("description").description("프로세스의 설명")
                        ),
                        responseFields(PROCESS_RESPONSE_FIELD_DESCRIPTORS)
                                .andWithPrefix("applicants[].", APPLICANT_RESPONSE_FIELD_DESCRIPTORS)
                ))
                .when().patch("/v1/processes/{processId}", process.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("조건에 맞지 않는 이름으로 변경 시도 시, 400을 응답한다.")
    @Test
    void update_invalidName() {
        // given
        Process process = processRepository.save(ProcessFixture.applyType(dashboard));
        ProcessUpdateRequest processUpdateRequest = new ProcessUpdateRequest("", "description");

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(processUpdateRequest)
                .filter(document(
                        "process/update-fail/invalid-name",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("processId").description("수정될 프로세스의 id")),
                        requestFields(
                                fieldWithPath("processName").description("조건에 맞지 않는 프로세스 이름"),
                                fieldWithPath("description").description("수정될 프로세스 설명")
                        )
                ))
                .when().patch("/v1/processes/{processId}", process.getId())
                .then().log().all().statusCode(400);
    }

    @DisplayName("존재하지 않는 프로세스 변경 시도 시, 404를 응답한다.")
    @Test
    void update_processNotFound() {
        // given
        Long invalidProcessId = -1L;
        processRepository.save(ProcessFixture.applyType(dashboard));
        ProcessUpdateRequest processUpdateRequest = new ProcessUpdateRequest("임시 과정", "수정된 프로세스");

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(processUpdateRequest)
                .filter(document(
                        "process/update-fail/process-not-found",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("processId").description("수정될 프로세스의 id")),
                        requestFields(
                                fieldWithPath("processName").description("조건에 맞지 않는 프로세스 이름"),
                                fieldWithPath("description").description("수정될 프로세스 설명")
                        )
                ))
                .when().patch("/v1/processes/{processId}", invalidProcessId)
                .then().log().all().statusCode(404);
    }

    @DisplayName("프로세스 삭제 성공 시, 204를 응답한다.")
    @Test
    void delete() {
        // given
        Process process = processRepository.save(ProcessFixture.interview(dashboard));

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .filter(document(
                        "process/delete",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("processId").description("생성할 프로세스의 대시보드 id"))
                ))
                .when().delete("/v1/processes/{processId}", process.getId())
                .then().log().all().statusCode(204);
    }

    @DisplayName("존재하지 않는 프로세스 삭제 시도 시, 404를 응답한다.")
    @Test
    void delete_processNotFound() {
        // given
        Long invalidId = -1L;

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .filter(document(
                        "process/delete-fail/process-not-found",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("processId").description("삭제할 프로세스의 id"))
                ))
                .when().delete("/v1/processes/{processId}", invalidId)
                .then().log().all().statusCode(404);
    }

    @DisplayName("처음 혹은 마지막 프로세스 삭제 시도 시, 400을 응답한다.")
    @Test
    void delete_endOrder() {
        // given
        Process process = processRepository.save(ProcessFixture.applyType(dashboard));

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .filter(document(
                        "process/delete-fail/process-order-first-or-last",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("processId").description("삭제할 프로세스의 id"))
                ))
                .when().delete("/v1/processes/{processId}", process.getId())
                .then().log().all().statusCode(400);
    }

    @DisplayName("지원자가 존재하는 프로세스 삭제 시도 시, 400을 응답한다.")
    @Test
    void delete_applicantExist() {
        // given
        Process process = processRepository.save(ProcessFixture.interview(dashboard));
        applicantRepository.save(ApplicantFixture.pendingDobby(process));

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .filter(document(
                        "process/delete-fail/process-applicant-exist",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("processId").description("삭제할 프로세스의 id"))
                ))
                .when().delete("/v1/processes/{processId}", process.getId())
                .then().log().all().statusCode(400);
    }
}
