package com.cruru.email.controller;

import com.cruru.auth.annotation.ValidAuth;
import com.cruru.email.controller.request.EmailRequest;
import com.cruru.email.controller.request.SendVerificationCodeRequest;
import com.cruru.email.facade.EmailFacade;
import com.cruru.global.LoginProfile;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
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
}
