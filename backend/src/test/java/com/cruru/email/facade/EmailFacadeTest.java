package com.cruru.email.facade;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.email.controller.dto.EmailRequest;
import com.cruru.email.domain.Email;
import com.cruru.email.service.EmailService;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.EmailFixture;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

@DisplayName("발송 내역 파사드 테스트")
class EmailFacadeTest extends ServiceTest {

    @SpyBean
    EmailService emailService;
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private EmailFacade emailFacade;

    @DisplayName("이메일을 비동기로 발송하고, 발송 내역을 저장한다.")
    @Test
    void sendAndSave() {
        // given
        Mockito.doAnswer(invocation -> {
            TimeUnit.SECONDS.sleep(1);
            return null;
        }).when(javaMailSender).send(any(MimeMessage.class));

        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby());
        EmailRequest request = new EmailRequest(
                defaultClub.getId(),
                List.of(applicant.getId()),
                EmailFixture.SUBJECT,
                EmailFixture.APPROVE_CONTENT,
                null
        );

        // when
        emailFacade.send(request);

        // then
        verify(javaMailSender, times(0)).send(any(MimeMessage.class));
        await().atMost(2, TimeUnit.SECONDS).untilAsserted(() -> {
            verify(javaMailSender, times(1)).send(any(MimeMessage.class));
            verify(emailService, times(1)).save(any(Email.class));
        });
    }
}
