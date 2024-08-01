package com.cruru.applyform.controller;

import com.cruru.applyform.controller.dto.ApplyFormSubmitRequest;
import com.cruru.applyform.service.ApplyFormService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/applyform")
@RequiredArgsConstructor
public class ApplyFormController {

    private final ApplyFormService applyFormService;

    @PostMapping("/{applyform_id}/submit")
    public ResponseEntity<Void> submit(
            @RequestBody @Valid ApplyFormSubmitRequest request,
            @PathVariable(name = "applyform_id") long applyFormId
    ) {
        applyFormService.submit(request, applyFormId);
        return ResponseEntity.created(URI.create("/v1/applyform/" + applyFormId)).build();
    }
}
