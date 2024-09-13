package com.cruru.email.service;

import com.cruru.applicant.domain.Applicant;
import com.cruru.club.domain.Club;
import com.cruru.email.domain.Email;
import com.cruru.email.domain.repository.EmailRepository;
import com.cruru.email.exception.EmailSendFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    @Transactional
    public void send(Club from, Applicant to, String subject, String text, List<MultipartFile> files) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to.getEmail());
            helper.setSubject(subject);
            helper.setText(text);
            if (existsFile(files)) {
                for (MultipartFile file : files) {
                    helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
                }
            }

            mailSender.send(message);
            log.info("sent email: from={}, to={}, subject={}", from.getId(), to.getEmail(), subject);
        } catch (MessagingException | MailException e) {
            throw new EmailSendFailedException(from.getId(), to.getEmail());
        }
    }

    private boolean existsFile(List<MultipartFile> files) {
        return files != null && !files.isEmpty();
    }

    @Transactional
    public void save(Club from, Applicant to, String subject, String text) {
        emailRepository.save(new Email(from, to, subject, text));
    }
}
