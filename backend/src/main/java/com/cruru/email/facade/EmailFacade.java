package com.cruru.email.facade;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.club.domain.Club;
import com.cruru.club.service.ClubService;
import com.cruru.email.controller.request.EmailRequest;
import com.cruru.email.controller.request.SendVerificationCodeRequest;
import com.cruru.email.controller.request.VerifyCodeRequest;
import com.cruru.email.controller.response.EmailHistoryResponse;
import com.cruru.email.controller.response.EmailHistoryResponses;
import com.cruru.email.domain.Email;
import com.cruru.email.dto.EmailQueueMessage;
import com.cruru.email.exception.EmailAttachmentsException;
import com.cruru.email.exception.EmailConflictException;
import com.cruru.email.exception.badrequest.EmailDuplicatedRequestException;
import com.cruru.email.service.EmailKeywordConverter;
import com.cruru.email.service.EmailQueueService;
import com.cruru.email.service.EmailRedisClient;
import com.cruru.email.service.EmailService;
import com.cruru.email.util.FileUtil;
import com.cruru.email.util.VerificationCodeUtil;
import com.cruru.member.service.MemberService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EmailFacade {

    private final EmailService emailService;
    private final ClubService clubService;
    private final ApplicantService applicantService;
    private final MemberService memberService;
    private final EmailRedisClient emailRedisClient;
    private final EmailKeywordConverter emailKeywordConverter;
    private final EmailQueueService emailQueueService;

    public void send(EmailRequest request) {
        Club from = clubService.findById(request.clubId());
        List<Applicant> applicants = applicantService.findAllByIds(request.applicantIds());
        sendAndQueue(from, applicants, request.subject(), request.content(), request.files());
    }

    private void sendAndQueue(Club from, List<Applicant> tos, String subject, String text, List<MultipartFile> files) {
        List<File> tempFiles = saveTempFiles(from, subject, files);
        List<String> attachmentPaths = tempFiles.stream().map(File::getAbsolutePath).toList();


        tos.forEach(to -> {
            // 이메일 내용에 대해 동적 키워드 변환을 미리 적용
            String convertedContent = emailKeywordConverter.convert(text, from, to);
            // EmailQueueMessage 생성
            EmailQueueMessage message = new EmailQueueMessage(
                    from.getId(),
                    to.getId(),
                    to.getEmail(),
                    subject,
                    convertedContent,
                    attachmentPaths
            );
            // 큐에 등록 (이미 등록된 요청은 중복으로 등록되지 않음)
            emailQueueService.enqueueEmail(message);
        });
    }

    private List<File> saveTempFiles(Club from, String subject, List<MultipartFile> files) {
        try {
            return FileUtil.saveTempFiles(files);
        } catch (IOException e) {
            throw new EmailAttachmentsException(from.getId(), subject);
        }
    }

    public void sendVerificationCode(SendVerificationCodeRequest request) {
        String email = request.email();
        String verificationCode = VerificationCodeUtil.generateVerificationCode();
        validateEmailExists(email);
        emailRedisClient.saveVerificationCode(email, verificationCode);
        emailService.sendVerificationCode(email, verificationCode);
    }

    private void validateEmailExists(String email) {
        if (memberService.existsByEmail(email)) {
            throw new EmailConflictException();
        }
    }

    public void verifyCode(VerifyCodeRequest request) {
        String email = request.email();
        String inputVerificationCode = request.verificationCode();
        String storedVerificationCode = emailRedisClient.getVerificationCode(email);

        VerificationCodeUtil.verify(storedVerificationCode, inputVerificationCode);
        emailRedisClient.saveVerifiedEmail(email);
    }

    public EmailHistoryResponses read(long clubId, long applicantId) {
        Club club = clubService.findById(clubId);
        Applicant applicant = applicantService.findById(applicantId);
        List<Email> emails = emailService.findAllByFromAndTo(club, applicant);
        return new EmailHistoryResponses(emails.stream()
                .map(this::toEmailResponse)
                .toList());
    }

    private EmailHistoryResponse toEmailResponse(Email email) {
        return new EmailHistoryResponse(
                email.getSubject(),
                email.getContent(),
                email.getCreatedDate(),
                email.getStatus()
        );
    }
}
