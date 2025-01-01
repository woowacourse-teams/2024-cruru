package com.cruru.member.acceptance;

import static org.mockito.Mockito.doNothing;

import com.cruru.auth.service.AuthService;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.email.service.EmailRedisClient;
import com.cruru.member.controller.request.EmailChangeRequest;
import com.cruru.member.controller.request.PasswordChangeRequest;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.AcceptanceTest;
import com.cruru.util.fixture.ClubFixture;
import com.cruru.util.fixture.MemberFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class MemberAcceptanceTest extends AcceptanceTest {

    @MockBean
    private EmailRedisClient emailRedisClient;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private AuthService authService;

    private Member member;
    private String newToken;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(MemberFixture.DOBBY);
        clubRepository.save(ClubFixture.create(member));
        newToken = authService.createAccessToken(member.getEmail(), member.getRole()).getToken();
    }

    @DisplayName("사용자는 다른 사용자의 이메일을 변경할 수 없다.")
    @Test
    void userCannotChangeAnotherUsersEmail() {
        // given
        String changeEmail = "change@email.com";
        EmailChangeRequest request = new EmailChangeRequest(changeEmail);
        doNothing().when(emailRedisClient).verifyEmail(request.email());

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("accessToken", newToken)
                .body(request)
                .when().patch("/v1/members/{memberId}/email", defaultMember.getId())
                .then().log().all().statusCode(403);
    }

    @DisplayName("사용자는 다른 사용자의 비밀번호를 변경할 수 없다.")
    @Test
    void userCannotChangeAnotherUsersPassword() {
        // given
        String changePassword = "NewPassword123!!";
        PasswordChangeRequest request = new PasswordChangeRequest(changePassword);

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("accessToken", newToken)
                .body(request)
                .when().patch("/v1/members/{memberId}/password", defaultMember.getId())
                .then().log().all().statusCode(403);
    }
}
