package com.cruru.email.service;

import com.cruru.applicant.domain.Applicant;
import com.cruru.club.domain.Club;
import com.cruru.email.domain.Email;
import com.cruru.email.controller.dto.EmailRequest;
import com.cruru.email.domain.repository.EmailRepository;
import com.cruru.email.exception.EmailSendFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;

    @Transactional
    public void send(EmailRequest request) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(request.to().toArray(new String[0]));
            helper.setSubject(request.subject());
            helper.setText(request.text());
            if (existsFile(request)) {
                for (MultipartFile file : request.files()) {
                    helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
                }
            }

            mailSender.send(message);
            log.info("sent email: from={}, to={}, subject={}", request.clubId(), request.to(), request.subject());
        } catch (MessagingException | MailException e) {
            throw new EmailSendFailedException(request.clubId(), request.to());
        }
    }

    private boolean existsFile(EmailRequest request) {
        return !CollectionUtils.isEmpty(request.files());
    }

    public void save(EmailRequest request, Club from, Applicant to) {
        emailRepository.save(new Email(from, to, request.subject(), request.text()));
    }
}
