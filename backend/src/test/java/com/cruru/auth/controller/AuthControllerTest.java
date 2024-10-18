package com.cruru.auth.controller;

import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.cookies.CookieDocumentation.responseCookies;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.cruru.auth.controller.request.LoginRequest;
import com.cruru.auth.domain.Token;
import com.cruru.auth.service.AuthService;
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

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("auth/login",
                        requestFields(
                                fieldWithPath("email").description("사용자 이메일"),
                                fieldWithPath("password").description("사용자 패스워드")
                        ),
                        responseFields(fieldWithPath("clubId").description("동아리의 id")),
                        responseHeaders(headerWithName("Set-Cookie").description("인증 쿠키 설정")),
                        responseCookies(
                                cookieWithName("accessToken").description("Access Token"),
                                cookieWithName("refreshToken").description("Refresh Token")
                        )
                ))
                .when().post("/v1/auth/login")
                .then().log().all().statusCode(200);
    }

    @DisplayName("잘못된 password로 로그인 시도 시 401을 응답한다.")
    @Test
    void login_unverifiedPassword() {
        // given
        LoginRequest request = new LoginRequest(member.getEmail(), "wrongPassword");

        // when&then
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

    @DisplayName("존재하지 않는 이메일로 로그인 시도 시 404를 응답한다.")
    @Test
    void login_emailNotFound() {
        // given
        LoginRequest request = new LoginRequest("invalid@email.com", member.getPassword());

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("auth/login-fail/email-not-found",
                        requestFields(
                                fieldWithPath("email").description("존재하지 않는 이메일"),
                                fieldWithPath("password").description("사용자 패스워드")
                        )
                ))
                .when().post("/v1/auth/login")
                .then().log().all().statusCode(404);
    }

    @DisplayName("로그아웃을 성공하면 204를 반환한다.")
    @Test
    void logout() {
        // given&when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .filter(document("auth/logout",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        responseHeaders(headerWithName("Set-Cookie").description("인증 해제 쿠키 설정"))
                ))
                .when().post("/v1/auth/logout")
                .then().log().all().statusCode(204);
    }

    @DisplayName("토큰이 없는 사용자가 로그아웃을 시도할 경우 401을 반환한다.")
    @Test
    void logout_withNoToken() {
        // given&when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .filter(document("auth/logout-fail/token-not-found"))
                .when().post("/v1/auth/logout")
                .then().log().all().statusCode(401);
    }
}
