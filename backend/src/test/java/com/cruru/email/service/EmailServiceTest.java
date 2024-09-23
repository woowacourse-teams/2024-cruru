package com.cruru.email.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.email.domain.Email;
import com.cruru.email.domain.repository.EmailRepository;
import com.cruru.util.ServiceTest;
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
}
