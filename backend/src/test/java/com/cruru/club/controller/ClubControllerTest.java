package com.cruru.club.controller;

import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.cruru.club.controller.request.ClubCreateRequest;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.ControllerTest;
import com.cruru.util.fixture.MemberFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("동아리 컨트롤러 테스트")
class ClubControllerTest extends ControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("동아리 생성 성공 시, 201을 응답한다.")
    @Test
    void create() {
        // given
        Member member = memberRepository.save(MemberFixture.DOBBY);
        String name = "연합 동아리";
        ClubCreateRequest request = new ClubCreateRequest(name);
        String url = String.format("/v1/clubs?memberId=%d", member.getId());

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("club/create/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(parameterWithName("memberId").description("동아리를 생성할 사용자의 id")),
                        requestFields(fieldWithPath("name").description("생성할 동아리의 이름"))
                ))
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
        String url = String.format("/v1/clubs?memberId=%d", invalidMemberId);

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("club/create-fail/member-not-found/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(parameterWithName("memberId").description("존재하지 않는 사용자의 id")),
                        requestFields(fieldWithPath("name").description("생성할 동아리의 이름"))
                ))
                .when().post(url)
                .then().log().all().statusCode(404);
    }

    @DisplayName("동아리 생성 시 조건에 맞지 않는 이름을 입력한 경우, 400을 응답한다.")
    @Test
    void create_invalidName() {
        // given
        String name = "";
        Member member = memberRepository.save(MemberFixture.DOBBY);
        ClubCreateRequest request = new ClubCreateRequest(name);
        String url = String.format("/v1/clubs?memberId=%d", member.getId());

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("club/create-fail/invalid-name/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(parameterWithName("memberId").description("동아리를 생성할 사용자의 id")),
                        requestFields(fieldWithPath("name").description("조건에 맞지 않는 동아리의 이름"))
                ))
                .when().post(url)
                .then().log().all().statusCode(400);
    }
}
