package com.cruru.email.controller;

import com.cruru.applicant.domain.Applicant;
import com.cruru.auth.annotation.RequireAuth;
import com.cruru.auth.annotation.ValidAuth;
import com.cruru.club.domain.Club;
import com.cruru.email.controller.request.EmailRequest;
import com.cruru.email.controller.request.SendVerificationCodeRequest;
import com.cruru.email.controller.request.VerifyCodeRequest;
import com.cruru.email.controller.response.EmailHistoryResponses;
import com.cruru.email.facade.EmailFacade;
import com.cruru.global.LoginProfile;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailFacade emailFacade;

    @PostMapping("/send")
    @ValidAuth
    public ResponseEntity<Void> send(
            @Valid @ModelAttribute EmailRequest request,
            LoginProfile loginProfile
    ) {
        emailFacade.send(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verification-code")
    public ResponseEntity<Void> sendVerificationCode(
            @RequestBody @Valid SendVerificationCodeRequest request
    ) {
        emailFacade.sendVerificationCode(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Void> verifyCode(@Valid @RequestBody VerifyCodeRequest request) {
        emailFacade.verifyCode(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{clubId}/{applicantId}")
    @ValidAuth
    public ResponseEntity<EmailHistoryResponses> read(
            @RequireAuth(targetDomain = Club.class) @PathVariable Long clubId,
            @RequireAuth(targetDomain = Applicant.class) @PathVariable Long applicantId,
            LoginProfile loginProfile
    ) {
        EmailHistoryResponses emailHistoryResponses = emailFacade.read(clubId, applicantId);
        return ResponseEntity.ok(emailHistoryResponses);
    }
}
