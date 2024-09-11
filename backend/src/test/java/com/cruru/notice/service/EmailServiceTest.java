package com.cruru.notice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.notice.controller.dto.EmailRequest;
import com.cruru.notice.domain.Notice;
import com.cruru.notice.domain.repository.NoticeRepository;
import com.cruru.util.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("발송 내역 서비스 테스트")
class EmailServiceTest extends ServiceTest {

    @Autowired
    private NoticeRepository noticeRepository;

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
        List<Notice> notices = noticeRepository.findAll();
        Notice actual = notices.get(0);
        assertAll(
                () -> assertThat(notices.size()).isEqualTo(1),
                () -> assertThat(actual.getSubject()).isEqualTo(subject),
                () -> assertThat(actual.getText()).isEqualTo(text)
        );
    }
}
