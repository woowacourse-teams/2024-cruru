package com.cruru.dashboard.controller;

import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.dashboard.controller.dto.DashboardCreateRequest;
import com.cruru.util.ControllerTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("대시보드 컨트롤러 테스트")
class DashboardControllerTest extends ControllerTest {

    @Autowired
    private ClubRepository clubRepository;

    private Club club;

    @BeforeEach
    void setUp() {
        club = clubRepository.save(new Club("크루루 동아리", null));
    }

    @DisplayName("대시보드 생성 성공 시, 201을 응답한다.")
    @Test
    void read() {
        // given
        DashboardCreateRequest request = new DashboardCreateRequest("크루루대시보드");
        String url = String.format("/v1/dashboards?club_id=%d", club.getId());

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post(url)
                .then().log().all().statusCode(201);
    }
}
