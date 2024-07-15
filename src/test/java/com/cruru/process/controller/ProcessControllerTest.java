package com.cruru.process.controller;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.controller.dto.ProcessesResponse;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProcessControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DashboardRepository dashboardRepository;

    private Dashboard dashboard;
    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        dashboard = dashboardRepository.save(new Dashboard("name", null));
    }

    @DisplayName("Id에 해당하는 대시보드의 프로세스 목록과 지원자 정보를 조회한다.")
    @Test
    void read() {
        RestAssured.given().log().all()
                .when().get("/api/v1/processes?dashboard_id=" + dashboard.getId())
                .then().log().all().statusCode(200);
    }
}
