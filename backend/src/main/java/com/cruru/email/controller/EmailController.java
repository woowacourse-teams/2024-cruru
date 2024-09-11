package com.cruru.email.controller;

import com.cruru.email.facade.EmailFacade;
import com.cruru.email.controller.dto.EmailRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailFacade emailFacade;

    @PostMapping
    public ResponseEntity<Void> send(@Valid @ModelAttribute EmailRequest request) {
        emailFacade.send(request);
        return ResponseEntity.ok().build();
    }
}
