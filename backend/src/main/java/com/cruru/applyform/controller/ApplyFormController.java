package com.cruru.applyform.controller;

import com.cruru.applyform.controller.dto.ApplyFormResponse;
import com.cruru.applyform.controller.dto.ApplyFormSubmitRequest;
import com.cruru.applyform.service.facade.ApplyFormFacade;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/applyform")
@RequiredArgsConstructor
public class ApplyFormController {

    private final ApplyFormFacade applyFormFacade;

    @PostMapping("/{applyformId}/submit")
    public ResponseEntity<Void> submit(
            @RequestBody @Valid ApplyFormSubmitRequest request,
            @PathVariable("applyformId") long applyFormId
    ) {
        applyFormFacade.submit(applyFormId, request);
        return ResponseEntity.created(URI.create("/v1/applyform/" + applyFormId)).build();
    }

    @GetMapping("/{applyformId}")
    public ResponseEntity<ApplyFormResponse> read(@PathVariable("applyformId") long applyFormId) {
        ApplyFormResponse response = applyFormFacade.readApplyFormById(applyFormId);
        return ResponseEntity.ok(response);
    }
}
