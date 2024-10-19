package com.cruru.email.facade;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.club.domain.Club;
import com.cruru.club.service.ClubService;
import com.cruru.email.controller.request.EmailRequest;
import com.cruru.email.controller.request.SendVerificationCodeRequest;
import com.cruru.email.controller.request.VerifyCodeRequest;
import com.cruru.email.exception.EmailAttachmentsException;
import com.cruru.email.exception.badrequest.VerificationCodeMismatchException;
import com.cruru.email.exception.badrequest.VerificationCodeNotFoundException;
import com.cruru.email.service.EmailRedisClient;
import com.cruru.email.service.EmailService;
import com.cruru.email.util.FileUtil;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
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
    private final EmailRedisClient emailRedisClient;
    private final Random random = new Random();

    public void send(EmailRequest request) {
        Club from = clubService.findById(request.clubId());
        List<Applicant> applicants = request.applicantIds()
                .stream()
                .map(applicantService::findById)
                .toList();
        sendAndSave(from, applicants, request.subject(), request.content(), request.files());
    }

    private void sendAndSave(Club from, List<Applicant> tos, String subject, String text, List<MultipartFile> files) {
        List<File> tempFiles = saveTempFiles(from, subject, files);

        List<CompletableFuture<Void>> futures = tos.stream()
                .map(to -> emailService.send(from, to, subject, text, tempFiles))
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

    public void sendVerificationCode(SendVerificationCodeRequest request) {
        String email = request.email();
        String verificationCode = String.format("%06d", random.nextInt(999999));

        emailRedisClient.saveVerificationCode(email, verificationCode);
        emailService.sendVerificationCode(email, verificationCode);
    }

    public void verifyCode(VerifyCodeRequest request) {
        String email = request.email();
        String inputVerificationCode = request.verificationCode();
        String storedVerificationCode = emailRedisClient.getVerificationCode(email);

        verify(storedVerificationCode, inputVerificationCode);
    }

    private void verify(String storedVerificationCode, String inputVerificationCode) {
        if (storedVerificationCode == null) {
            throw new VerificationCodeNotFoundException();
        }

        if (!storedVerificationCode.equals(inputVerificationCode)) {
            throw new VerificationCodeMismatchException();
        }
    }
}
