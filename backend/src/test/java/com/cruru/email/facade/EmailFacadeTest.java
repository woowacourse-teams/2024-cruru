package com.cruru.email.facade;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.email.controller.request.EmailRequest;
import com.cruru.email.controller.request.SendVerificationCodeRequest;
import com.cruru.email.controller.request.VerifyCodeRequest;
import com.cruru.email.domain.Email;
import com.cruru.email.exception.EmailConflictException;
import com.cruru.email.exception.badrequest.VerificationCodeMismatchException;
import com.cruru.email.exception.badrequest.VerificationCodeNotFoundException;
import com.cruru.email.service.EmailRedisClient;
import com.cruru.email.service.EmailService;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.EmailFixture;
import com.cruru.util.fixture.MemberFixture;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

@DisplayName("발송 내역 파사드 테스트")
class EmailFacadeTest extends ServiceTest {

    @SpyBean
    EmailService emailService;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EmailFacade emailFacade;

    @MockBean
    private EmailRedisClient emailRedisClient;

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

    @DisplayName("이미 가입된 이메일로 인증을 시도하면 예외가 발생한다.")
    @Test
    void sendVerificationCode() {
        // given
        String email = MemberFixture.DOBBY.getEmail();
        memberRepository.save(MemberFixture.DOBBY);
        SendVerificationCodeRequest request = new SendVerificationCodeRequest(email);

        // when&then
        assertThatThrownBy(() -> emailFacade.sendVerificationCode(request))
                .isInstanceOf(EmailConflictException.class);
    }

    @DisplayName("이메일 인증 성공 시, 인증 코드가 검증된다.")
    @Test
    void verifyCode() {
        // given
        String email = "test@example.com";
        String verificationCode = "123456";
        VerifyCodeRequest request = new VerifyCodeRequest(email, verificationCode);

        when(emailRedisClient.getVerificationCode(email)).thenReturn(verificationCode);

        // when
        emailFacade.verifyCode(request);

        // then
        verify(emailRedisClient, times(1)).getVerificationCode(email);
    }

    @DisplayName("저장된 인증 코드가 없으면 예외가 발생한다.")
    @Test
    void verifyCode_verificationCodeNotFoundException() {
        // given
        String email = "test@example.com";
        String verificationCode = "123456";
        VerifyCodeRequest request = new VerifyCodeRequest(email, verificationCode);

        when(emailRedisClient.getVerificationCode(email)).thenReturn(null);

        // when&then
        assertThatThrownBy(() -> emailFacade.verifyCode(request))
                .isInstanceOf(VerificationCodeNotFoundException.class)
                .hasMessage("인증 코드가 존재하지 않거나 만료되었습니다.");
    }

    @DisplayName("저장된 인증 코드와 일치하지 않으면 예외가 발생한다.")
    @Test
    void verifyCode_verificationCodeMismatchException() {
        // given
        String email = "test@example.com";
        String correctCode = "123456";
        String wrongCode = "654321";
        VerifyCodeRequest request = new VerifyCodeRequest(email, wrongCode);

        when(emailRedisClient.getVerificationCode(email)).thenReturn(correctCode);

        // when&then
        assertThatThrownBy(() -> emailFacade.verifyCode(request))
                .isInstanceOf(VerificationCodeMismatchException.class)
                .hasMessage("인증 코드가 일치하지 않습니다.");
    }
}
