package com.cruru.email.facade;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.email.controller.dto.EmailRequest;
import com.cruru.email.domain.Email;
import com.cruru.email.domain.repository.EmailRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.EmailFixture;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

@DisplayName("발송 내역 파사드 테스트")
class EmailFacadeTest extends ServiceTest {

    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private EmailFacade emailFacade;

    @DisplayName("이메일을 발송한 후 발송 내역을 저장한다.")
    @Test
    void sendEmail() {
        // given
        Mockito.doReturn(Mockito.mock(MimeMessage.class))
                .when(javaMailSender).createMimeMessage();
        Mockito.doNothing()
                .when(javaMailSender).send(Mockito.any(MimeMessage.class));

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
}
