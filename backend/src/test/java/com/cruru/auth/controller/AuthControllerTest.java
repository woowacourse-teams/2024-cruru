package com.cruru.auth.controller;

import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.cookies.CookieDocumentation.responseCookies;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.cruru.auth.controller.dto.LoginRequest;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.ControllerTest;
import com.cruru.util.fixture.ClubFixture;
import com.cruru.util.fixture.MemberFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("인증 컨트롤러 테스트")
class AuthControllerTest extends ControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ClubRepository clubRepository;

    private Member member;

    @BeforeEach
    void setup() {
        clubRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        member = memberRepository.save(MemberFixture.ADMIN);
        clubRepository.save(ClubFixture.create(member));
    }

    @DisplayName("로그인 성공 시 200을 응답한다.")
    @Test
    void login() {
        // given
        LoginRequest request = new LoginRequest(member.getEmail(), "qwer1234");

        // when&&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("auth/login",
                        requestFields(
                                fieldWithPath("email").description("사용자 이메일"),
                                fieldWithPath("password").description("사용자 패스워드")
                        ),
                        responseFields(
                                fieldWithPath("clubId").description("동아리의 id")
                        ),
                        responseCookies(cookieWithName("token").description("사용자 토큰"))
                ))
                .when().post("/v1/auth/login")
                .then().log().all().statusCode(200);
    }

    @DisplayName("잘못된 password로 로그인 시도시 401을 응답한다.")
    @Test
    void login_unverifiedPassword() {
        // given
        LoginRequest request = new LoginRequest(member.getEmail(), "wrongPassword");

        // when&&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("auth/login-fail/invalid-password",
                        requestFields(
                                fieldWithPath("email").description("사용자 이메일"),
                                fieldWithPath("password").description("잘못된 패스워드")
                        )
                ))
                .when().post("/v1/auth/login")
                .then().log().all().statusCode(401);
    }

    @DisplayName("로그아웃을 성공하면 204를 반환한다.")
    @Test
    void logout() {
        // given&when&&then
        RestAssured.given(spec).log().all()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .filter(document("auth/logout",
                        requestCookies(cookieWithName("token").description("사용자 토큰"))
                ))
                .when().post("/v1/auth/logout")
                .then().log().all().statusCode(204);
    }
}
