package com.cruru.email.facade;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.applyform.service.ApplyFormService;
import com.cruru.club.domain.Club;
import com.cruru.club.service.ClubService;
import com.cruru.email.controller.request.EmailRequest;
import com.cruru.email.controller.request.SendVerificationCodeRequest;
import com.cruru.email.controller.request.VerifyCodeRequest;
import com.cruru.email.controller.response.EmailHistoryResponse;
import com.cruru.email.controller.response.EmailHistoryResponses;
import com.cruru.email.domain.Email;
import com.cruru.email.domain.EmailKeyword;
import com.cruru.email.exception.EmailAttachmentsException;
import com.cruru.email.exception.EmailConflictException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailFacade {

    private final EmailService emailService;
    private final ClubService clubService;
    private final ApplicantService applicantService;
    private final MemberService memberService;
    private final EmailRedisClient emailRedisClient;
    private final ApplyFormService applyFormService;

    public void send(EmailRequest request) {
        Club from = clubService.findById(request.clubId());
        List<Applicant> applicants = applicantService.findAllByIds(request.applicantIds());
        sendAndSave(from, applicants, request.subject(), request.content(), request.files());
    }

    private void sendAndSave(Club from, List<Applicant> tos, String subject, String text, List<MultipartFile> files) {
        List<File> tempFiles = saveTempFiles(from, subject, files);

        List<CompletableFuture<Void>> futures = tos.stream()
                .map(to -> emailService.send(from, to, subject, applyTemplate(text, from, to), tempFiles))
                .map(future -> future.thenAccept(emailService::save))
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenRun(() -> FileUtil.deleteFiles(tempFiles));
    }

    private List<File> saveTempFiles(Club from, String subject, List<MultipartFile> files) {
        try {
            return FileUtil.saveTempFiles(files);
        } catch (IOException e) {
            throw new EmailAttachmentsException(from.getId(), subject);
        }
    }

    private String applyTemplate(String content, Club from, Applicant to) {
        content = EmailKeyword.APPLICANT_NAME.replace(content, to.getName());
        content = EmailKeyword.CLUB_NAME.replace(content, from.getName());
        content = EmailKeyword.APPLY_FORM_TITLE.replace(content,
                applyFormService.findByDashboard(to.getDashboard()).getTitle());
        content = EmailKeyword.PROCESS_NAME.replace(content, to.getProcess().getName());
        return content;
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
                email.getIsSucceed()
        );
    }
}
