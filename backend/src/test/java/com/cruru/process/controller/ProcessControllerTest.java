package com.cruru.process.controller;

import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.controller.dto.ProcessCreateRequest;
import com.cruru.process.controller.dto.ProcessUpdateRequest;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ControllerTest;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.ProcessFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("프로세스 컨트롤러 테스트")
class ProcessControllerTest extends ControllerTest {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private ProcessRepository processRepository;

    private Dashboard dashboard;

    @BeforeEach
    void setUp() {
        dashboard = dashboardRepository.save(DashboardFixture.backend());
        applyFormRepository.save(ApplyFormFixture.backend(dashboard));
    }

    @DisplayName("프로세스 조회 성공 시, 200을 응답한다.")
    @Test
    void read() {
        // given
        String url = String.format("/v1/processes?dashboardId=%d", dashboard.getId());

        // when&then
        RestAssured.given().log().all()
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
        RestAssured.given().log().all()
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
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(processCreateRequest)
                .when().post(url)
                .then().log().all().statusCode(201);
    }

    @DisplayName("존재하는 프로세스의 이름과 설명 변경 성공시, 200을 응답한다.")
    @Test
    void update_success() {
        // given
        Process process = processRepository.save(ProcessFixture.first(dashboard));
        ProcessUpdateRequest processUpdateRequest = new ProcessUpdateRequest("임시 과정", "수정된 프로세스");
        String url = String.format("/v1/processes/%d", process.getId());

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(processUpdateRequest)
                .when().patch(url)
                .then().log().all().statusCode(200);
    }

    @DisplayName("프로세스 삭제 성공 시, 204를 응답한다.")
    @Test
    void delete() {
        // given
        Process process = processRepository.save(ProcessFixture.interview(dashboard));
        String url = String.format("/v1/processes/%d", process.getId());

        // when&then
        RestAssured.given().log().all()
                .when().delete(url)
                .then().log().all().statusCode(204);
    }
}
