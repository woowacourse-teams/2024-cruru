package com.cruru.question.controller;

import com.cruru.question.controller.request.QuestionUpdateRequests;
import com.cruru.question.service.facade.QuestionFacade;
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
    public ResponseEntity<Void> update(
            @RequestParam(name = "applyformId") Long applyFormId,
            @RequestBody @Valid QuestionUpdateRequests request
    ) {
        questionFacade.update(request, applyFormId);
        return ResponseEntity.ok().build();
    }
}
