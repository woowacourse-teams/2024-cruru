package com.cruru.notice.controller;

import com.cruru.notice.controller.dto.EmailRequest;
import com.cruru.notice.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public void sendEmail(@Valid @ModelAttribute EmailRequest request) {
        emailService.send(request);
    }
}
