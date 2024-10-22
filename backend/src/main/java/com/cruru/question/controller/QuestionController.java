package com.cruru.question.controller;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.auth.annotation.RequireAuth;
import com.cruru.auth.annotation.ValidAuth;
import com.cruru.global.LoginProfile;
import com.cruru.question.controller.request.QuestionUpdateRequests;
import com.cruru.question.facade.QuestionFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionFacade questionFacade;

    @PatchMapping
    @ValidAuth
    public ResponseEntity<Void> update(
            @RequireAuth(targetDomain = ApplyForm.class) @RequestParam(name = "applyformId") String applyFormId,
            @RequestBody @Valid QuestionUpdateRequests request,
            LoginProfile loginProfile
    ) {
        questionFacade.update(request, applyFormId);
        return ResponseEntity.ok().build();
    }
}
