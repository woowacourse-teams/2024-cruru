package com.cruru.club.controller;

import static com.cruru.util.fixture.MemberFixture.createMember;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.cruru.club.controller.dto.ClubCreateRequest;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.ControllerTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("동아리 컨트롤러 테스트")
class ClubControllerTest extends ControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(createMember());
    }

    @DisplayName("동아리 생성 성공 시, 201을 응답한다.")
    @Test
    void create() {
        // given
        String name = "club name";
        ClubCreateRequest request = new ClubCreateRequest(name);
        String url = String.format("/v1/clubs?member_id=%d", member.getId());

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("club/create/",
                        queryParameters(parameterWithName("member_id").description("동아리를 생성한 사용자의 id")),
                        requestFields(fieldWithPath("name").description("생성할 동아리의 이름"))
                ))
                .when().post(url)
                .then().log().all().statusCode(201);
    }

    @DisplayName("동아리 생성 시 멤버가 존재하지 않을 경우, 404을 응답한다.")
    @Test
    void create_memberNotFound() {
        // given
        String name = "club name";
        ClubCreateRequest request = new ClubCreateRequest(name);
        long invalidMemberId = -1;
        String url = String.format("/v1/clubs?member_id=%d", invalidMemberId);

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("club/create-fail/member-not-found/",
                        queryParameters(parameterWithName("member_id").description("존재하지 않는 사용자의 id")),
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
        ClubCreateRequest request = new ClubCreateRequest(name);
        String url = String.format("/v1/clubs?member_id=%d", member.getId());

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("club/create-fail/invalid-name/",
                        queryParameters(parameterWithName("member_id").description("존재하지 않는 사용자의 id")),
                        requestFields(fieldWithPath("name").description("조건에 맞지 않는 동아리의 이름"))
                ))
                .when().post(url)
                .then().log().all().statusCode(400);
    }
}
