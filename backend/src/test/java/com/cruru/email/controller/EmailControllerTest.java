package com.cruru.email.controller;

import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.util.ControllerTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.EmailFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.io.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("이메일 컨트롤러 테스트")
class EmailControllerTest extends ControllerTest {

    @Autowired
    private ApplicantRepository applicantRepository;

    @DisplayName("이메일 발송 성공 시, 200을 응답한다.")
    @Test
    void send() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby());
        String subject = EmailFixture.SUBJECT;
        String content = EmailFixture.APPROVE_CONTENT;
        File file = new File(getClass().getClassLoader().getResource("static/email_test.txt").getFile());

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .multiPart("clubId", defaultClub.getId())
                .multiPart("applicantIds", applicant.getId())
                .multiPart("subject", subject)
                .multiPart("content", content)
                .multiPart("files", file)
                .contentType(ContentType.MULTIPART)
                .filter(document("email/send",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        requestParts(
                                partWithName("clubId").description("발송 동아리 id"),
                                partWithName("applicantIds").description("수신자 id 목록"),
                                partWithName("subject").description("이메일 제목"),
                                partWithName("content").description("이메일 본문"),
                                partWithName("files").description("이메일 첨부 파일")
                        )
                ))
                .when().post("/v1/emails/send")
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 지원자 id를 수신자로 설정한 경우, 404를 응답한다.")
    @Test
    void send_invalidRequest() {
        // given
        long invalidId = -1;
        String subject = EmailFixture.SUBJECT;
        String content = EmailFixture.APPROVE_CONTENT;
        File file = new File(getClass().getClassLoader().getResource("static/email_test.txt").getFile());

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .multiPart("clubId", defaultClub.getId())
                .multiPart("applicantIds", invalidId)
                .multiPart("subject", subject)
                .multiPart("content", content)
                .multiPart("files", file)
                .contentType(ContentType.MULTIPART)
                .filter(document("email/send-fail/invalid-email",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        requestParts(
                                partWithName("clubId").description("발송 동아리 id"),
                                partWithName("applicantIds").description("적절하지 않은 수신자 id가 포함된 목록"),
                                partWithName("subject").description("이메일 제목"),
                                partWithName("content").description("이메일 본문"),
                                partWithName("files").description("이메일 첨부 파일")
                        )
                ))
                .when().post("/v1/emails/send")
                .then().log().all().statusCode(404);
    }

    @DisplayName("존재하지 않는 동아리 id를 발송자로 설정한 경우, 404를 응답한다.")
    @Test
    void send_clubNotExist() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby());
        long invalidId = -1;
        long email = applicant.getId();
        String subject = EmailFixture.SUBJECT;
        String content = EmailFixture.APPROVE_CONTENT;
        File file = new File(getClass().getClassLoader().getResource("static/email_test.txt").getFile());

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .multiPart("clubId", invalidId)
                .multiPart("applicantIds", email)
                .multiPart("subject", subject)
                .multiPart("content", content)
                .multiPart("files", file)
                .contentType(ContentType.MULTIPART)
                .filter(document("email/send-fail/club-not-found",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        requestParts(
                                partWithName("clubId").description("존재하지 않는 발송 동아리 id"),
                                partWithName("applicantIds").description("수신자 id 목록"),
                                partWithName("subject").description("이메일 제목"),
                                partWithName("content").description("이메일 본문"),
                                partWithName("files").description("이메일 첨부 파일")
                        )
                ))
                .when().post("/v1/emails/send")
                .then().log().all().statusCode(404);
    }
}
