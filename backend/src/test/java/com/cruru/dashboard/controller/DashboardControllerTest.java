package com.cruru.dashboard.controller;

import static com.cruru.util.fixture.ClubFixture.createClub;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

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
        club = clubRepository.save(createClub());
    }

    @DisplayName("대시보드 생성 성공 시, 201을 응답한다.")
    @Test
    void read() {
        // given
        DashboardCreateRequest request = new DashboardCreateRequest("dashboard name");
        String url = String.format("/v1/dashboards?club_id=%d", club.getId());

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("dashboard/create/",
                        queryParameters(parameterWithName("club_id").description("대시보드를 생성할 동아리")),
                        requestFields(fieldWithPath("name").description("대시보드 이름"))
                ))
                .when().post(url)
                .then().log().all().statusCode(201);
    }

    @DisplayName("존재하지 않는 동아리로 대시보드 생성 시, 404를 응답한다.")
    @Test
    void read_clubNotFound() {
        // given
        DashboardCreateRequest request = new DashboardCreateRequest("dashboard name");
        long invalidClubId = -1;
        String url = String.format("/v1/dashboards?club_id=%d", invalidClubId);

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("dashboard/create-fail/club-not-found",
                        queryParameters(parameterWithName("club_id").description("존재하지 않는 동아리")),
                        requestFields(fieldWithPath("name").description("대시보드 이름"))
                ))
                .when().post(url)
                .then().log().all().statusCode(404);
    }
}
