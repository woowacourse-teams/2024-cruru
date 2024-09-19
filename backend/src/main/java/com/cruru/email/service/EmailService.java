package com.cruru.email.service;

import com.cruru.applicant.domain.Applicant;
import com.cruru.club.domain.Club;
import com.cruru.email.domain.Email;
import com.cruru.email.domain.repository.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;

    public List<File> saveTempFiles(List<MultipartFile> files) throws IOException {
        if (files == null || files.isEmpty()) {
            return new ArrayList<>();
        }

        List<File> tempFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            File tempFile = File.createTempFile("temp_", "_" + file.getOriginalFilename());
            file.transferTo(tempFile);
            tempFiles.add(tempFile);
        }
        return tempFiles;
    }

    @Async
    public CompletableFuture<Email> send(
            Club from, Applicant to, String subject, String content, List<File> tempFiles) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to.getEmail());
            helper.setSubject(subject);
            helper.setText(content);
            if (hasFile(tempFiles)) {
                addAttachments(helper, tempFiles);
            }
            mailSender.send(message);

            log.info("이메일 전송 성공: from={}, to={}, subject={}", from.getId(), to.getEmail(), subject);
            return CompletableFuture.completedFuture(new Email(from, to, subject, content, true));
        } catch (MessagingException | MailException e) {
            log.info("이메일 전송 실패: from={}, to={}, subject={}", from.getId(), to.getEmail(), e.getMessage());
            return CompletableFuture.completedFuture(new Email(from, to, subject, content, false));
        }
    }

    private boolean hasFile(List<File> files) {
        return files != null && !files.isEmpty();
    }

    private void addAttachments(MimeMessageHelper helper, List<File> files) throws MessagingException {
        for (File file : files) {
            addAttachment(helper, file);
        }
    }

    private void addAttachment(MimeMessageHelper helper, File file) throws MessagingException {
        String fileName = file.getName();
        if (isValidateFileName(fileName)) {
            helper.addAttachment(fileName, file);
        }
    }

    private boolean isValidateFileName(String fileName) {
        return fileName != null && !fileName.isEmpty();
    }

    @Transactional
    public void save(Email email) {
        emailRepository.save(email);
    }

    @Transactional
    public void saveAll(List<Email> emails) {
        emailRepository.saveAll(emails);
    }
}
