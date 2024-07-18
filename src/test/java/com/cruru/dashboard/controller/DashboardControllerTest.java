package com.cruru.dashboard.controller;

import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.dashboard.controller.dto.DashboardCreateDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@DisplayName("대시보드 컨트롤러 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DashboardControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ClubRepository clubRepository;

    private Club club;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        club = clubRepository.save(new Club("크루루 동아리", null));
    }

    @DisplayName("대시보드 생성 성공 시, 201을 응답한다.")
    @Test
    void read() {
        DashboardCreateDto request = new DashboardCreateDto("크루루대시보드");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/v1/dashboards?club_id=" + club.getId())
                .then().log().all().statusCode(201);
    }
}
