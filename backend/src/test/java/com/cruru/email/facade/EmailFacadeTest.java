package com.cruru.email.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cruru.applicant.domain.Applicant;
import com.cruru.club.domain.Club;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.email.controller.request.EmailRequest;
import com.cruru.email.controller.request.SendVerificationCodeRequest;
import com.cruru.email.controller.request.VerifyCodeRequest;
import com.cruru.email.controller.response.EmailHistoryResponse;
import com.cruru.email.controller.response.EmailHistoryResponses;
import com.cruru.email.domain.Email;
import com.cruru.email.domain.repository.EmailRepository;
import com.cruru.email.dto.EmailQueueMessage;
import com.cruru.email.exception.EmailConflictException;
import com.cruru.email.exception.badrequest.VerificationCodeMismatchException;
import com.cruru.email.exception.badrequest.VerificationCodeNotFoundException;
import com.cruru.email.service.EmailKeywordConverter;
import com.cruru.email.service.EmailQueueService;
import com.cruru.email.service.EmailRedisClient;
import com.cruru.email.service.EmailService;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.EmailFixture;
import com.cruru.util.fixture.MemberFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@DisplayName("발송 내역 파사드 테스트")
class EmailFacadeTest extends ServiceTest {

    @SpyBean
    EmailService emailService;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailFacade emailFacade;

    @MockBean
    private EmailQueueService emailQueueService;

    @MockBean
    private EmailRedisClient emailRedisClient;

    @MockBean
    private EmailKeywordConverter emailKeywordConverter;

    @DisplayName("이미 가입된 이메일로 인증을 시도하면 예외가 발생한다.")
    @Test
    void sendVerificationCode() {
        // given
        String email = MemberFixture.DOBBY.getEmail();
        memberRepository.save(MemberFixture.DOBBY);
        SendVerificationCodeRequest request = new SendVerificationCodeRequest(email);

        // when&then
        assertThatThrownBy(() -> emailFacade.sendVerificationCode(request)).isInstanceOf(EmailConflictException.class);
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
        assertThatThrownBy(() -> emailFacade.verifyCode(request)).isInstanceOf(VerificationCodeNotFoundException.class)
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
        assertThatThrownBy(() -> emailFacade.verifyCode(request)).isInstanceOf(VerificationCodeMismatchException.class)
                .hasMessage("인증 코드가 일치하지 않습니다.");
    }

    @DisplayName("동아리와 지원자 id로 이메일을 조회한다.")
    @Test
    void read() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby());
        Email email = emailRepository.save(EmailFixture.rejectEmail(defaultClub, applicant));

        // when
        EmailHistoryResponses emailHistoryResponses = emailFacade.read(defaultClub.getId(), applicant.getId());

        // then
        assertThat(emailHistoryResponses.emailHistoryResponses()).hasSize(1);
        EmailHistoryResponse emailHistoryResponse = emailHistoryResponses.emailHistoryResponses().get(0);
        assertAll(
                () -> assertThat(emailHistoryResponse.subject()).isEqualTo(email.getSubject()),
                () -> assertThat(emailHistoryResponse.content()).isEqualTo(email.getContent()),
                () -> assertThat(emailHistoryResponse.status()).isEqualTo(email.getStatus())
        );
    }

    @DisplayName("이메일 발송 요청이 Redis 큐에 정상 등록된다.")
    @Test
    void send_enqueueEmails() {
        // given
        // 임시 파일을 위한 MockMultipartFile 생성
        var file = new MockMultipartFile("file", "test.txt", "text/plain", "Test data".getBytes());

        // 테스트용 Applicant 생성
        Applicant applicant = ApplicantFixture.pendingDobby();
        applicant = applicantRepository.save(applicant);

        // EmailRequest 생성
        var request = new EmailRequest(
                defaultClub.getId(),
                List.of(applicant.getId()),
                "Test Subject",
                "Test Content",
                List.of(file)
        );

        // EmailKeywordConverter가 원본 내용을 그대로 반환하도록 설정
        when(emailKeywordConverter.convert(anyString(), any(Club.class), any(Applicant.class)))
                .thenReturn("Test Content");

        // 이메일 큐 등록 메서드가 항상 true를 반환한다고 가정
        when(emailQueueService.enqueueEmail(any(EmailQueueMessage.class))).thenReturn(true);

        // when
        emailFacade.send(request);

        // then
        // 수신자 수 만큼 (여기서는 1명) EmailQueueService.enqueueEmail이 호출되어야 함
        verify(emailQueueService, times(1)).enqueueEmail(any(EmailQueueMessage.class));
    }
}
