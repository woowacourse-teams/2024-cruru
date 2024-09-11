package com.cruru.email.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.email.controller.dto.EmailRequest;
import com.cruru.email.domain.Email;
import com.cruru.email.domain.repository.EmailRepository;
import com.cruru.util.ServiceTest;
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
        String subject = "[우아한테크코스] 7기 최종 심사 결과 안내";
        String text = "우아한테크코스 합격을 진심으로 축하합니다!";
        EmailRequest request = new EmailRequest(
                null,
                null,
                subject,
                text,
                null
        );

        // when
        emailService.save(request, null, null);

        // then
        List<Email> emails = emailRepository.findAll();
        Email actual = emails.get(0);
        assertAll(
                () -> assertThat(emails.size()).isEqualTo(1),
                () -> assertThat(actual.getSubject()).isEqualTo(subject),
                () -> assertThat(actual.getText()).isEqualTo(text)
        );
    }
}
