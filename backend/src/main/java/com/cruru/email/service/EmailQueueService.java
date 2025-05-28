package com.cruru.email.service;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.club.domain.Club;
import com.cruru.club.service.ClubService;
import com.cruru.email.dto.EmailQueueMessage;
import com.cruru.email.exception.EmailRedisException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailQueueService {

    private static final String EMAIL_REQUEST_KEY_PREFIX = "email_send:";
    private static final String EMAIL_QUEUE_KEY = "email_queue";
    private static final String EMAIL_PROCESSING_KEY = "email_queue_processing";
    private static final long IDEMPOTENCY_TTL_MINUTES = 60;

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final ClubService clubService;
    private final ApplicantService applicantService;

    public boolean enqueueEmail(EmailQueueMessage message) {
        try {
            String uniqueKey = generateUniqueKey(message.getToEmail(), message.getSubject(), message.getContent());
            String redisUniqueKey = EMAIL_REQUEST_KEY_PREFIX + uniqueKey;

            // 중복 여부 체크: setIfAbsent가 true이면 최초 등록, false면 중복
            Boolean success = redisTemplate.opsForValue().setIfAbsent(
                    redisUniqueKey,
                    "1",
                    IDEMPOTENCY_TTL_MINUTES,
                    TimeUnit.MINUTES
            );
            if (success == null || !success) {
                log.info("중복 이메일 발송 요청 감지: {}", redisUniqueKey);
                return false;
            }
            // JSON으로 변환 후, Redis 리스트 큐에 적재
            String messageJson = objectMapper.writeValueAsString(message);
            redisTemplate.opsForList().rightPush(EMAIL_QUEUE_KEY, messageJson);
            log.info("이메일 요청 큐에 등록: {}", messageJson);
            return true;
        } catch (Exception e) {
            log.error("이메일 요청 등록 실패", e);
            return false;
        }
    }

    private String generateUniqueKey(String toEmail, String subject, String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String combined = toEmail + subject + content;
            byte[] digest = md.digest(combined.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new EmailRedisException("Redis 고유 키 생성 중 오류");
        }
    }

    @Scheduled(fixedDelay = 5000)
    public void processQueue() {
        try {
            // 매 스케줄마다 처리 중인 메일 복구 시도
            recoverStuckMessages();

            String messageJson;
            // leftPop 대신 rightPopAndLeftPush를 사용하여 원자적으로 처리 중 상태로 이동
            while ((messageJson = redisTemplate.opsForList().rightPopAndLeftPush(EMAIL_QUEUE_KEY, EMAIL_PROCESSING_KEY))
                    != null) {
                log.info("큐에서 이메일 요청 처리 시작: {}", messageJson);
                EmailQueueMessage message = objectMapper.readValue(messageJson, EmailQueueMessage.class);
                Club from = clubService.findById(message.getClubId());
                Applicant to = applicantService.findById(message.getApplicantId());
                List<File> attachments = null;
                if (message.getAttachmentPaths() != null) {
                    attachments = message.getAttachmentPaths()
                            .stream()
                            .map(File::new)
                            .toList();
                }

                // 이메일 전송: send()는 비동기로 처리 후 CompletableFuture<Email> 반환
                String finalMessageJson = messageJson;
                emailService.send(from, to, message.getSubject(), message.getContent(), attachments)
                        .thenAccept(email -> {
                            // 성공적으로 처리되면 처리 중 리스트에서 제거
                            redisTemplate.opsForList().remove(EMAIL_PROCESSING_KEY, 1, finalMessageJson);
                            emailService.save(email);
                            log.info("이메일 전송 완료 및 처리 대기열에서 제거");
                        }).exceptionally(ex -> {
                            log.error("이메일 전송 중 오류 발생: {}", ex.getMessage());
                            // 오류 발생 시 처리 중 리스트에서 제거 후 다시 메인 큐로 이동
                            redisTemplate.opsForList().remove(EMAIL_PROCESSING_KEY, 1, finalMessageJson);
                            redisTemplate.opsForList().rightPush(EMAIL_QUEUE_KEY, finalMessageJson);
                            log.info("전송 실패한 메일을 재처리 큐에 등록");
                            return null;
                        });
            }
        } catch (Exception e) {
            log.error("이메일 Redis 큐 처리 중 예외 발생", e);
        }
    }

    private void recoverStuckMessages() {
        int count = 0;
        while (redisTemplate.opsForList().rightPopAndLeftPush(EMAIL_PROCESSING_KEY, EMAIL_QUEUE_KEY) != null) {
            count++;
        }
        if (count > 0) {
            log.info("애플리케이션 시작 시 처리되지 않은 메일 {}건 복원 완료", count);
        }
    }
}
