package com.cruru.notice.service;

import com.cruru.applicant.domain.Applicant;
import com.cruru.club.domain.Club;
import com.cruru.notice.controller.dto.EmailRequest;
import com.cruru.notice.domain.Notice;
import com.cruru.notice.domain.NoticeType;
import com.cruru.notice.domain.repository.NoticeRepository;
import com.cruru.notice.exception.EmailSendFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final NoticeRepository noticeRepository;

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
        } catch (MessagingException e) {
            throw new EmailSendFailedException();
        }
    }

    private boolean existsFile(EmailRequest request) {
        return !CollectionUtils.isEmpty(request.files());
    }

    public void save(EmailRequest request, Club from, Applicant to) {
        noticeRepository.save(new Notice(from, to, request.subject(), request.text(), NoticeType.EMAIL));
    }
}
