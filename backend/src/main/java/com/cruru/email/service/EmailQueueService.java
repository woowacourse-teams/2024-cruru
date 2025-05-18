package com.cruru.email.service;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.club.domain.Club;
import com.cruru.club.service.ClubService;
import com.cruru.email.dto.EmailQueueMessage;
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
            throw new RuntimeException("고유 키 생성 중 오류", e);
        }
    }

    @Scheduled(fixedDelay = 5000)
    public void processQueue() {
        try {
            String messageJson;
            while ((messageJson = redisTemplate.opsForList().leftPop(EMAIL_QUEUE_KEY)) != null) {
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
                emailService.send(from, to, message.getSubject(), message.getContent(), attachments)
                        .thenAccept(emailService::save)
                        .exceptionally(ex -> {
                            log.error("이메일 전송 중 오류 발생: {}", ex.getMessage());
                            return null;
                        });
            }
        } catch (Exception e) {
            log.error("큐 처리 중 예외 발생", e);
        }
    }
}
