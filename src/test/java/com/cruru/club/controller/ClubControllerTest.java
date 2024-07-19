package com.cruru.club.controller;

import com.cruru.club.controller.dto.ClubCreateRequest;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@DisplayName("동아리 컨트롤러 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClubControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        member = memberRepository.save(new Member("test", "test1234", "test@test.com"));
    }

    @DisplayName("동아리 생성 성공 시, 201을 응답한다.")
    @Test
    void create() {
        // given
        String name = "연합 동아리";
        ClubCreateRequest request = new ClubCreateRequest(name);
        String url = String.format("/v1/clubs?member_id=%d", member.getId());

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post(url)
                .then().log().all().statusCode(201);
    }

    @DisplayName("멤버가 존재하지 않을 경우, 404을 응답한다.")
    @Test
    void create_memberNotFound() {
        // given
        String name = "연합 동아리";
        ClubCreateRequest request = new ClubCreateRequest(name);
        long invalidMemberId = -1;
        String url = String.format("/v1/clubs?member_id=%d", invalidMemberId);

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post(url)
                .then().log().all().statusCode(404);
    }
}
