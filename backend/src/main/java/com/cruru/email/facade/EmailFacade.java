package com.cruru.email.facade;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.club.domain.Club;
import com.cruru.club.service.ClubService;
import com.cruru.email.controller.dto.EmailRequest;
import com.cruru.email.service.EmailService;
import java.util.List;
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

    @Transactional
    public void send(EmailRequest request) {
        Club from = clubService.findById(request.clubId());
        request.applicantIds()
                .stream()
                .map(applicantService::findById)
                .forEach(to -> sendAndSave(from, to, request.subject(), request.content(), request.files()));
    }

    @Transactional
    public void sendAndSave(Club from, Applicant to, String subject, String content, List<MultipartFile> files) {
        emailService.save(from, to, subject, content);
        emailService.send(from, to, subject, content, files);
    }
}
