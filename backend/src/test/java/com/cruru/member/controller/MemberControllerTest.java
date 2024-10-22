package com.cruru.member.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.cruru.email.exception.NotVerifiedEmailException;
import com.cruru.email.service.EmailRedisClient;
import com.cruru.member.controller.request.MemberCreateRequest;
import com.cruru.util.ControllerTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("사용자 컨트롤러 테스트")
class MemberControllerTest extends ControllerTest {

    @MockBean
    private EmailRedisClient emailRedisClient;

    private static Stream<MemberCreateRequest> InvalidMemberSignUpRequest() {
        String validName = "크루루";
        String validMail = "mail@mail.com";
        String validPassword = "newPassword214!";
        String validPhone = "01012341234";
        return Stream.of(
                new MemberCreateRequest(null, validMail, validPassword, validPhone),
                new MemberCreateRequest("", validMail, validPassword, validPhone),
                new MemberCreateRequest(validName, null, validPassword, validPhone),
                new MemberCreateRequest(validName, "", validPassword, validPhone),
                new MemberCreateRequest(validName, "notMail", validPassword, validPhone),
                new MemberCreateRequest(validName, validMail, null, validPhone),
                new MemberCreateRequest(validName, validMail, "", validPhone),
                new MemberCreateRequest(validName, validMail, validPassword, null),
                new MemberCreateRequest(validName, validMail, validPassword, "")
        );
    }

    @DisplayName("사용자를 생성 성공 시 201을 응답한다.")
    @Test
    void create() {
        // given
        MemberCreateRequest request = new MemberCreateRequest("크루루", "mail@mail.com", "newPassword214!", "01012341234");
        doNothing().when(emailRedisClient).verifyEmail(request.email());

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("member/signup",
                        requestFields(
                                fieldWithPath("clubName").description("동아리명"),
                                fieldWithPath("email").description("사용자 이메일"),
                                fieldWithPath("password").description("사용자 패스워드"),
                                fieldWithPath("phone").description("사용자 전화번호")
                        )
                ))
                .when().post("/v1/members/signup")
                .then().log().all().statusCode(201);
    }

    @DisplayName("유효하지 않은 요청으로 가입 시 400을 응답한다.")
    @ParameterizedTest
    @MethodSource("InvalidMemberSignUpRequest")
    void create_invalidEmail(MemberCreateRequest request) {
        // given
        doNothing().when(emailRedisClient).verifyEmail(request.email());

        //when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("member/signup-fail/invalid-request",
                        requestFields(
                                fieldWithPath("clubName").description("동아리명"),
                                fieldWithPath("email").description("사용자 이메일"),
                                fieldWithPath("password").description("사용자 패스워드"),
                                fieldWithPath("phone").description("사용자 전화번호")
                        )
                ))
                .when().post("/v1/members/signup")
                .then().log().all().statusCode(400);
    }

    @DisplayName("인증되지 않은 사용자가 회원가입할 경우, 401을 반환한다.")
    @Test
    void create_notVerifiedEmail() {
        // given
        MemberCreateRequest request = new MemberCreateRequest("크루루", "mail@mail.com", "newPassword214!", "01012341234");
        doThrow(NotVerifiedEmailException.class).when(emailRedisClient).verifyEmail(request.email());

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("member/signup-fail/not-verified-email",
                        requestFields(
                                fieldWithPath("clubName").description("동아리명"),
                                fieldWithPath("email").description("인증되지 않은 사용자 이메일"),
                                fieldWithPath("password").description("사용자 패스워드"),
                                fieldWithPath("phone").description("사용자 전화번호")
                        )
                ))
                .when().post("/v1/members/signup")
                .then().log().all().statusCode(401);
    }
}
