package com.cruru.member.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.cruru.member.controller.dto.MemberCreateRequest;
import com.cruru.util.ControllerTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("사용자 컨트롤러 테스트")
class MemberControllerTest extends ControllerTest {

    @DisplayName("사용자를 생성 성공 시 201을 응답한다.")
    @Test
    void create() {
        // given
        MemberCreateRequest request = new MemberCreateRequest("mail@mail.com", "qwer1234", "01012341234");

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("member/create/",
                        requestFields(
                                fieldWithPath("email").description("생성할 사용자의 이메일"),
                                fieldWithPath("password").description("생성할 사용자의 비밀번호"),
                                fieldWithPath("phone").description("생성할 사용자의 전화번호")
                        )))
                .when().post("/v1/members")
                .then().log().all().statusCode(201);
    }
}
