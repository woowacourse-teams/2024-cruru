package com.cruru.email.facade;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.club.domain.Club;
import com.cruru.club.service.ClubService;
import com.cruru.email.controller.dto.EmailRequest;
import com.cruru.email.exception.EmailAttachmentsException;
import com.cruru.email.service.EmailService;
import com.cruru.email.util.FileUtil;
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
}
