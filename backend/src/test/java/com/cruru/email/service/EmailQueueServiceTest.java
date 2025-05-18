package com.cruru.email.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.club.domain.Club;
import com.cruru.club.service.ClubService;
import com.cruru.email.domain.Email;
import com.cruru.email.domain.EmailStatus;
import com.cruru.email.dto.EmailQueueMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@DisplayName("EmailQueueService 단위 테스트")
class EmailQueueServiceTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @Mock
    private ListOperations<String, String> listOperations;

    @Mock
    private EmailService emailService;

    @Mock
    private ClubService clubService;

    @Mock
    private ApplicantService applicantService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private EmailQueueService emailQueueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        emailQueueService = new EmailQueueService(
                redisTemplate,
                objectMapper,
                emailService,
                clubService,
                applicantService
        );
    }

    @Test
    @DisplayName("중복이 아닌 이메일 요청은 큐에 정상 등록된다.")
    void enqueueEmail_success() {
        // given
        EmailQueueMessage message = new EmailQueueMessage(
                1L,
                2L,
                "test@example.com",
                "Test Subject",
                "Test Content",
                List.of("/tmp/file1.txt")
        );
        when(valueOperations.setIfAbsent(anyString(), eq("1"), eq(60L), eq(TimeUnit.MINUTES))).thenReturn(Boolean.TRUE);

        // when
        boolean result = emailQueueService.enqueueEmail(message);

        // then
        assertThat(result).isTrue();
        verify(listOperations, times(1)).rightPush(eq("email_queue"), anyString());
    }

    @Test
    @DisplayName("중복된 이메일 요청은 큐에 등록되지 않는다.")
    void enqueueEmail_duplicate() {
        // given
        EmailQueueMessage message = new EmailQueueMessage(
                1L,
                2L,
                "duplicate@example.com",
                "Subject",
                "Content",
                List.of("/tmp/file1.txt")
        );
        when(valueOperations.setIfAbsent(
                anyString(),
                eq("1"),
                eq(60L),
                eq(TimeUnit.MINUTES)
        )).thenReturn(Boolean.FALSE);

        // when
        boolean result = emailQueueService.enqueueEmail(message);

        // then
        assertThat(result).isFalse();
        verify(listOperations, never()).rightPush(anyString(), anyString());
    }

    @Test
    @DisplayName("큐에 저장된 이메일 요청을 꺼내어 처리한다.")
    void processQueue_processOneMessage() throws Exception {
        // given
        EmailQueueMessage message = new EmailQueueMessage(
                1L,
                2L,
                "process@example.com",
                "Subject",
                "Content",
                List.of("/tmp/file1.txt")
        );
        String messageJson = objectMapper.writeValueAsString(message);
        when(listOperations.leftPop("email_queue")).thenReturn(messageJson, null);

        Club dummyClub = mock(Club.class);
        Applicant dummyApplicant = mock(Applicant.class);
        when(clubService.findById(1L)).thenReturn(dummyClub);
        when(applicantService.findById(2L)).thenReturn(dummyApplicant);

        // emailService.send가 완료된 이메일 객체를 반환하도록 시뮬레이션
        Email dummyEmail = new Email(dummyClub, dummyApplicant, "Subject", "Content", EmailStatus.DELIVERED);
        when(emailService.send(
                eq(dummyClub),
                eq(dummyApplicant),
                eq("Subject"),
                eq("Content"),
                any(List.class)
        )).thenReturn(CompletableFuture.completedFuture(dummyEmail));

        // when
        emailQueueService.processQueue();

        // then
        verify(emailService, times(1)).send(
                eq(dummyClub),
                eq(dummyApplicant),
                eq("Subject"),
                eq("Content"),
                any(List.class)
        );
        verify(emailService, times(1)).save(any(Email.class));
    }

    @Test
    @DisplayName("큐가 비어있으면 아무런 처리도 하지 않는다.")
    void processQueue_emptyQueue() {
        // leftPop가 바로 null을 반환하도록 설정
        when(listOperations.leftPop("email_queue")).thenReturn(null);
        // when
        emailQueueService.processQueue();
        // then: 이메일 전송 관련 메서드가 호출되지 않음
        verify(emailService, never()).send(any(), any(), anyString(), anyString(), any(List.class));
    }

    @Test
    @DisplayName("큐 처리 중 예외가 발생해도 정상적으로 처리된다.")
    void processQueue_handleException() throws Exception {
        // given
        EmailQueueMessage message = new EmailQueueMessage(
                1L,
                2L,
                "process@example.com",
                "Subject",
                "Content",
                List.of("/tmp/file1.txt")
        );
        String messageJson = objectMapper.writeValueAsString(message);
        when(listOperations.leftPop("email_queue")).thenReturn(messageJson);

        // 예외 발생 시뮬레이션
        when(clubService.findById(1L)).thenThrow(new RuntimeException("테스트 예외"));

        // when
        emailQueueService.processQueue();

        // then
        // 예외가 발생해도 메서드가 정상적으로 종료되어야 함
        verify(clubService, times(1)).findById(1L);
        verify(emailService, never()).send(any(), any(), anyString(), anyString(), any(List.class));
    }

    @Test
    @DisplayName("JSON 파싱 오류가 발생해도 정상적으로 처리된다.")
    void processQueue_handleJsonParseException() {
        // given
        // 잘못된 JSON 형식의 메시지
        String invalidJson = "{invalid_json}";
        when(listOperations.leftPop("email_queue")).thenReturn(invalidJson, null);

        // when
        emailQueueService.processQueue();

        // then
        // 예외가 발생해도 메서드가 정상적으로 종료되어야 함
        verify(emailService, never()).send(any(), any(), anyString(), anyString(), any(List.class));
    }
}
