package com.cruru.email.facade;

import com.cruru.applicant.service.ApplicantService;
import com.cruru.club.domain.Club;
import com.cruru.club.service.ClubService;
import com.cruru.email.controller.dto.EmailRequest;
import com.cruru.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        request.to()
                .stream()
                .map(applicantService::findByEmail)
                .forEach(to -> emailService.save(request, from, to));
        emailService.send(request);
    }
}
