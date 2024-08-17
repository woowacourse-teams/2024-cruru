package com.cruru.member.controller;

import com.cruru.member.controller.dto.LoginRequest;
import com.cruru.member.controller.dto.MemberCreateRequest;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.ControllerTest;
import com.cruru.util.fixture.MemberFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("사용자 컨트롤러 테스트")
class MemberControllerTest extends ControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void tearDown() {
        member = memberRepository.save(MemberFixture.ADMIN);
    }

    @DisplayName("사용자를 생성 성공 시 201을 응답한다.")
    @Test
    void create() {
        // given
        MemberCreateRequest request = new MemberCreateRequest("크루루", "mail@mail.com", "newPassword214!", "01012341234");

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/v1/members/signup")
                .then().log().all().statusCode(201);
    }

    @DisplayName("로그인 성공 시 200을 응답한다.")
    @Test
    void login() {
        // given
        LoginRequest request = new LoginRequest(member.getEmail(), "qwer1234");

        // when&&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/v1/members/login")
                .then().log().all().statusCode(200);
    }

    @DisplayName("잘못된 password로 로그인 시도시 401을 응답한다.")
    @Test
    void login_unverfiedPassword() {
        // given
        LoginRequest request = new LoginRequest(member.getEmail(), "wrongPassword");

        // when&&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/v1/members/login")
                .then().log().all().statusCode(401);
    }
}
