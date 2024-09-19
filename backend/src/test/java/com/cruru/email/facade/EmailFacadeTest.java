package com.cruru.email.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.email.controller.dto.EmailRequest;
import com.cruru.email.domain.Email;
import com.cruru.email.domain.repository.EmailRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.EmailFixture;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@DisplayName("발송 내역 파사드 테스트")
class EmailFacadeTest extends ServiceTest {


    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private EmailFacade emailFacade;

    @Disabled
    @DisplayName("이메일을 발송한 후 발송 내역을 저장한다.")
    @Test
    void sendEmail() {
        // given
        Applicant applicant = ApplicantFixture.pendingDobby();
        applicantRepository.save(applicant);
        String subject = EmailFixture.SUBJECT;
        EmailRequest emailRequest = new EmailRequest(
                defaultClub.getId(),
                List.of(applicant.getId()),
                subject,
                EmailFixture.APPROVE_CONTENT,
                null
        );

        // when
        emailFacade.send(emailRequest);

        // then
        List<Email> saved = emailRepository.findAll();
        assertThat(saved).anyMatch(notice -> notice.getSubject().equals(subject));
    }

    @DisplayName("이메일을 비동기로 전송한다.")
    @Test
    void sendEmailMany() throws IOException {
        // given
        Mockito.doAnswer(invocation -> wait1second())
                .when(javaMailSender).send(any(MimeMessage.class));

        List<Applicant> applicants = applicantRepository.saveAll(
                List.of(
                        ApplicantFixture.pendingDobby(),
                        ApplicantFixture.pendingDobby(),
                        ApplicantFixture.pendingDobby(),
                        ApplicantFixture.pendingDobby(),
                        ApplicantFixture.pendingDobby(),

                        ApplicantFixture.pendingDobby(),
                        ApplicantFixture.pendingDobby(),
                        ApplicantFixture.pendingDobby(),
                        ApplicantFixture.pendingDobby(),
                        ApplicantFixture.pendingDobby()
                )
        );
        List<Long> ids = applicants.stream()
                .map(Applicant::getId)
                .toList();
        String subject = EmailFixture.SUBJECT;
        File file = new File(getClass().getClassLoader().getResource("static/email_test.txt").getFile());
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", input);
        EmailRequest emailRequest = new EmailRequest(
                defaultClub.getId(),
                ids,
                subject,
                EmailFixture.APPROVE_CONTENT,
                List.of(multipartFile)
        );

        // when
        long before = System.currentTimeMillis();
        emailFacade.send(emailRequest);
        long after = System.currentTimeMillis();

        // then
        System.out.println(after-before);
        assertThat(after - before).isLessThan(10000);
    }

    public Object wait1second() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
