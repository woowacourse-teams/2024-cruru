package com.cruru.process.controller;

import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.controller.dto.ProcessCreateRequest;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@DisplayName("프로세스 컨트롤러 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProcessControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ProcessRepository processRepository;

    private Dashboard dashboard;

    private Process process1;
    private Process process2;
    private Process process3;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        dashboard = dashboardRepository.save(new Dashboard("name", null));
        process1 = processRepository.save(new Process(0, "면접", "1차 면접", dashboard));
        process2 = processRepository.save(new Process(1, "면접", "1차 면접", dashboard));
        process3 = processRepository.save(new Process(2, "면접", "1차 면접", dashboard));
    }

    @DisplayName("프로세스 조회 성공 시, 200을 응답한다.")
    @Test
    void read() {
        RestAssured.given().log().all()
                .when().get("/api/v1/processes?dashboard_id=" + dashboard.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("프로세스 생성 성공 시, 201을 응답한다.")
    @Test
    void create() {
        ProcessCreateRequest processCreateRequest = new ProcessCreateRequest("면접", "1차 면접", 2);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(processCreateRequest)
                .when().post("/api/v1/processes?dashboard_id=" + dashboard.getId())
                .then().log().all().statusCode(201);
    }

    @DisplayName("프로세스 삭제 성공 시, 204를 응답한다.")
    @Test
    void delete() {
        RestAssured.given().log().all()
                .when().delete("/api/v1/processes/" + process2.getId())
                .then().log().all().statusCode(204);
    }
}
