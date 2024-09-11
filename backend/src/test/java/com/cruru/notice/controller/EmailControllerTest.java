package com.cruru.notice.controller;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.util.ControllerTest;
import com.cruru.util.fixture.ApplicantFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

@DisplayName("이메일 컨트롤러 테스트")
class EmailControllerTest extends ControllerTest {

    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private ApplicantRepository applicantRepository;

    @DisplayName("이메일 발송 성공 시, 200을 응답한다.")
    @Test
    void send() {
        // given
        Mockito.doReturn(Mockito.mock(MimeMessage.class))
                .when(javaMailSender).createMimeMessage();
        Mockito.doNothing()
                .when(javaMailSender).send(Mockito.any(MimeMessage.class));
        Applicant applicant = ApplicantFixture.pendingDobby();
        applicantRepository.save(applicant);
        String subject = "[우아한테크코스] 7기 최종 심사 결과 안내";
        String text = "우아한테크코스 합격을 진심으로 축하합니다!";

        // when&then
        RestAssured.given().log().all()
                .cookie("token", token)
                .multiPart("clubId", defaultClub.getId())
                .multiPart("to", applicant.getEmail())
                .multiPart("subject", subject)
                .multiPart("text", text)
                .contentType(ContentType.MULTIPART)
                .when().post("/v1/emails")
                .then().log().all().statusCode(200);
    }

    @DisplayName("이메일 형식이 올바르지 않은 경우, 400을 응답한다.")
    @Test
    void send_invalidRequest() {
        // given
        Mockito.doReturn(Mockito.mock(MimeMessage.class))
                .when(javaMailSender).createMimeMessage();
        Mockito.doNothing()
                .when(javaMailSender).send(Mockito.any(MimeMessage.class));
        Applicant applicant = ApplicantFixture.pendingDobby();
        applicantRepository.save(applicant);
        String subject = "[우아한테크코스] 7기 최종 심사 결과 안내";
        String text = "우아한테크코스 합격을 진심으로 축하합니다!";

        // when&then
        RestAssured.given().log().all()
                .cookie("token", token)
                .multiPart("clubId", defaultClub.getId())
                .multiPart("to", "notEmail")
                .multiPart("subject", subject)
                .multiPart("text", text)
                .contentType(ContentType.MULTIPART)
                .when().post("/v1/emails")
                .then().log().all().statusCode(400);
    }

    @DisplayName("존재하지 않는 동아리 id를 발송자로 설정한 경우, 404를 응답한다.")
    @Test
    void send_clubNotExist() {
        // given
        Mockito.doReturn(Mockito.mock(MimeMessage.class))
                .when(javaMailSender).createMimeMessage();
        Mockito.doNothing()
                .when(javaMailSender).send(Mockito.any(MimeMessage.class));

        Applicant applicant = ApplicantFixture.pendingDobby();
        applicantRepository.save(applicant);
        long invalidId = -1;
        String email = applicant.getEmail();
        String subject = "[우아한테크코스] 7기 최종 심사 결과 안내";
        String text = "우아한테크코스 합격을 진심으로 축하합니다!";

        // when&then
        RestAssured.given().log().all()
                .cookie("token", token)
                .multiPart("clubId", invalidId)
                .multiPart("to", email)
                .multiPart("subject", subject)
                .multiPart("text", text)
                .contentType(ContentType.MULTIPART)
                .when().post("/v1/emails")
                .then().log().all().statusCode(404);
    }
}
