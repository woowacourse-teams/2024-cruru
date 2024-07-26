package com.cruru.process.controller;

import static com.cruru.util.fixture.ApplicantFixture.createApplicantDobby;
import static com.cruru.util.fixture.DashboardFixture.createBackendDashboard;
import static com.cruru.util.fixture.ProcessFixture.createFirstProcess;
import static com.cruru.util.fixture.ProcessFixture.createInterviewProcess;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.controller.dto.ProcessCreateRequest;
import com.cruru.process.controller.dto.ProcessUpdateRequest;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ControllerTest;
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

    private static final FieldDescriptor[] PROCESS_IN_DASHBOARD_FIELD_DESCRIPTORS = {
            fieldWithPath("process_id").description("프로세스의 id"),
            fieldWithPath("order_index").description("프로세스의 순서"),
            fieldWithPath("name").description("프로세스의 이름"),
            fieldWithPath("description").description("프로세스의 설명"),
            fieldWithPath("applicants").description("프로세스의 지원자 목록"),
            };

    private static final FieldDescriptor[] APPLICANTS_IN_DASHBOARD_FIELD_DESCRIPTORS = {
            fieldWithPath("applicant_id").description("지원자의 id"),
            fieldWithPath("applicant_name").description("지원자의 이름"),
            fieldWithPath("created_at").description("지원자들의 지원 날짜 및 시간"),
            fieldWithPath("is_rejected").description("지원자의 불합격 여부"),
            fieldWithPath("evaluation_count").description("지원자의 평가된 횟수")
    };

    private static final FieldDescriptor[] PROCESS_FIELD_DESCRIPTORS = {
            fieldWithPath("process_name").description("프로세스 이름"),
            fieldWithPath("description").description("프로세스 설명"),
            fieldWithPath("order_index").description("프로세스가 삽입될 순서")
    };

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    private Dashboard dashboard;

    @BeforeEach
    void setUp() {
        dashboard = dashboardRepository.save(createBackendDashboard());
    }

    @DisplayName("프로세스 목록 조회 성공 시, 200을 응답한다.")
    @Test
    void read() {
        // given
        String url = String.format("/v1/processes?dashboard_id=%d", dashboard.getId());
        Process process = processRepository.save(createFirstProcess(dashboard));
        applicantRepository.save(createApplicantDobby(process));

        // when&then
        RestAssured.given(spec).log().all()
                .filter(document("process/read/",
                        queryParameters(parameterWithName("dashboard_id").description("조회할 프로세스의 대시보드 id")),
                        responseFields(
                                fieldWithPath("processes").description("해당 대시보드의 프로세스 목록"))
                                .andWithPrefix("processes[].", PROCESS_IN_DASHBOARD_FIELD_DESCRIPTORS)
                                .andWithPrefix("processes[].applicants[].", APPLICANTS_IN_DASHBOARD_FIELD_DESCRIPTORS)
                ))
                .when().get(url)
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 대시보드로 프로세스 목록 조회 시도 시, 404를 응답한다.")
    @Test
    void read_dashboardNotFound() {
        // given
        long invalidDashboardId = 0;
        String url = String.format("/v1/processes?dashboard_id=%d", invalidDashboardId);

        // when&then
        RestAssured.given(spec).log().all()
                .filter(document("process/read-fail/dashboard-not-found/",
                        queryParameters(parameterWithName("dashboard_id").description("존재하지 않는 대시보드 id"))
                ))
                .when().get(url)
                .then().log().all().statusCode(404);
    }

    @DisplayName("프로세스 생성 성공 시, 201을 응답한다.")
    @Test
    void create() {
        // given
        ProcessCreateRequest processCreateRequest = new ProcessCreateRequest("name", "description", 1);
        String url = String.format("/v1/processes?dashboard_id=%d", dashboard.getId());

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(processCreateRequest)
                .filter(document("process/create/",
                        queryParameters(parameterWithName("dashboard_id").description("생성할 프로세스의 대시보드 id")),
                        requestFields(PROCESS_FIELD_DESCRIPTORS)))
                .when().post(url)
                .then().log().all().statusCode(201);
    }

    @DisplayName("존재하지 않는 대시보드의 프로세스 생성 시도 시, 404를 응답한다.")
    @Test
    void create_dashboardNotFound() {
        // given
        ProcessCreateRequest processCreateRequest = new ProcessCreateRequest("name", "description", 1);
        long invalidDashboardId = -1;
        String url = String.format("/v1/processes?dashboard_id=%d", invalidDashboardId);

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(processCreateRequest)
                .filter(document("process/create-fail/dashboard-not-found/",
                        queryParameters(parameterWithName("dashboard_id").description("존재하지 않는 대시보드 id")),
                        requestFields(PROCESS_FIELD_DESCRIPTORS)))
                .when().post(url)
                .then().log().all().statusCode(404);
    }

    @DisplayName("조건에 맞지 않는 이름의 프로세스 생성 시도 시, 400을 응답한다.")
    @Test
    void create_invalidName() {
        // given
        ProcessCreateRequest processCreateRequest = new ProcessCreateRequest("", "description", 1);
        String url = String.format("/v1/processes?dashboard_id=%d", dashboard.getId());

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(processCreateRequest)
                .filter(document("process/create-fail/invalid-name/",
                        queryParameters(parameterWithName("dashboard_id").description("생성할 프로세스의 대시보드 id")),
                        requestFields(
                                fieldWithPath("process_name").description("조건에 맞지 않는 프로세스 이름"),
                                fieldWithPath("description").description("프로세스 설명"),
                                fieldWithPath("order_index").description("프로세스가 삽입될 순서")
                        )))
                .when().post(url)
                .then().log().all().statusCode(400);
    }

    @DisplayName("프로세스가 최대일 때 생성 시도 시, 400을 응답한다.")
    @Test
    void create_processCountOvered() {
        // given
        processRepository.saveAll(List.of(
                new Process(0, "서류", "서류", dashboard),
                new Process(1, "코딩 테스트", "온라인", dashboard),
                new Process(2, "CS 테스트", "온라인", dashboard),
                new Process(3, "1차 면접", "화상 면접", dashboard),
                new Process(4, "최종 면접", "대면 면접", dashboard)
        ));
        ProcessCreateRequest processCreateRequest = new ProcessCreateRequest("name", "description", 3);
        String url = String.format("/v1/processes?dashboard_id=%d", dashboard.getId());

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(processCreateRequest)
                .filter(document("process/create-fail/process-count-overed/",
                        queryParameters(parameterWithName("dashboard_id").description("생성할 프로세스의 대시보드 id")),
                        requestFields(PROCESS_FIELD_DESCRIPTORS)
                ))
                .when().post(url)
                .then().log().all().statusCode(400);
    }

    @DisplayName("존재하는 프로세스의 이름과 설명 변경 성공 시, 200을 응답한다.")
    @Test
    void update() {
        // given
        Process process = processRepository.save(createFirstProcess(dashboard));
        applicantRepository.save(createApplicantDobby(process));
        ProcessUpdateRequest processUpdateRequest = new ProcessUpdateRequest("name", "description");

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(processUpdateRequest)
                .filter(document("process/update/",
                        pathParameters(parameterWithName("process_id").description("수정될 프로세스의 id")),
                        requestFields(
                                fieldWithPath("process_name").description("수정될 프로세스 이름"),
                                fieldWithPath("description").description("수정될 프로세스 설명")
                        ),
                        responseFields(PROCESS_IN_DASHBOARD_FIELD_DESCRIPTORS)
                                .andWithPrefix("applicants[].", APPLICANTS_IN_DASHBOARD_FIELD_DESCRIPTORS)
                ))
                .when().patch("/v1/processes/{process_id}", process.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("조건에 맞지 않는 이름으로 변경 시도 시, 400을 응답한다.")
    @Test
    void update_invalidName() {
        // given
        Process process = processRepository.save(createFirstProcess(dashboard));
        ProcessUpdateRequest processUpdateRequest = new ProcessUpdateRequest("", "description");

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(processUpdateRequest)
                .filter(document("process/update-failed/invalid-name",
                        pathParameters(parameterWithName("process_id").description("수정될 프로세스의 id")),
                        requestFields(
                                fieldWithPath("process_name").description("조건에 맞지 않는 프로세스 이름"),
                                fieldWithPath("description").description("수정될 프로세스 설명")
                        )
                ))
                .when().patch("/v1/processes/{process_id}", process.getId())
                .then().log().all().statusCode(400);
    }

    @DisplayName("프로세스 삭제 성공 시, 204를 응답한다.")
    @Test
    void delete() {
        // given
        Process process = processRepository.save(createInterviewProcess(dashboard));

        // when&then
        RestAssured.given(spec).log().all()
                .filter(document("process/delete",
                        pathParameters(parameterWithName("process_id").description("삭제할 프로세스의 id"))
                ))
                .when().delete("/v1/processes/{process_id}", process.getId())
                .then().log().all().statusCode(204);
    }

    @DisplayName("존재하지 않는 프로세스 삭제 시도 시, 404를 응답한다.")
    @Test
    void delete_processNotFound() {
        // given
        long invalidId = -1;

        // when&then
        RestAssured.given(spec).log().all()
                .filter(document("process/delete-fail/process-not-found",
                        pathParameters(parameterWithName("process_id").description("삭제할 프로세스의 id"))
                ))
                .when().delete("/v1/processes/{process_id}", invalidId)
                .then().log().all().statusCode(404);
    }

    @DisplayName("처음 또는 마지막 프로세스 삭제 시도 시, 400을 응답한다.")
    @Test
    void delete_endOrder() {
        // given
        Process process = processRepository.save(createFirstProcess(dashboard));

        // when&then
        RestAssured.given(spec).log().all()
                .filter(document("process/delete-fail/process-order-first-or-last",
                        pathParameters(parameterWithName("process_id").description("삭제할 프로세스의 id"))
                ))
                .when().delete("/v1/processes/{process_id}", process.getId())
                .then().log().all().statusCode(400);
    }

    @DisplayName("지원자가 존재하는 프로세스 삭제 시도 시, 400을 응답한다.")
    @Test
    void delete_applicantExist() {
        // given
        Process process = processRepository.save(createInterviewProcess(dashboard));
        applicantRepository.save(createApplicantDobby(process));

        // when&then
        RestAssured.given(spec).log().all()
                .filter(document("process/delete-fail/process-applicant-exist",
                        pathParameters(parameterWithName("process_id").description("삭제할 프로세스의 id"))
                ))
                .when().delete("/v1/processes/{process_id}", process.getId())
                .then().log().all().statusCode(400);
    }
}
