package com.cruru.email.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.email.domain.Email;
import com.cruru.email.domain.repository.EmailRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.EmailFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("발송 내역 서비스 테스트")
class EmailServiceTest extends ServiceTest {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ApplicantRepository applicantRepository;

    @DisplayName("발송 내역을 저장한다.")
    @Test
    void save() {
        // given
        String subject = EmailFixture.SUBJECT;
        String content = EmailFixture.APPROVE_CONTENT;

        // when
        emailService.save(EmailFixture.approveEmail());

        // then
        List<Email> emails = emailRepository.findAll();
        Email actual = emails.get(0);
        assertAll(
                () -> assertThat(emails).hasSize(1),
                () -> assertThat(actual.getSubject()).isEqualTo(subject),
                () -> assertThat(actual.getContent()).isEqualTo(content)
        );
    }

    @DisplayName("대상 지원자 목록에 따라 모두 삭제한다.")
    @Test
    void deleteAllByTos() {
        // given
        Applicant to1 = applicantRepository.save(ApplicantFixture.pendingDobby());
        Applicant to2 = applicantRepository.save(ApplicantFixture.pendingDobby());
        Applicant to3 = applicantRepository.save(ApplicantFixture.pendingDobby());
        List<Applicant> tos = List.of(to1, to2);

        Email email1 = emailRepository.save(EmailFixture.rejectEmail(null, to1));
        Email email2 = emailRepository.save(EmailFixture.rejectEmail(null, to2));
        Email email3 = emailRepository.save(EmailFixture.rejectEmail(null, to3));

        // when
        emailService.deleteAllByTos(tos);

        // then
        assertThat(emailRepository.findAll()).contains(email3)
                .doesNotContain(email1, email2);
    }
}
