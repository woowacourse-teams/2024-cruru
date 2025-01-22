package com.cruru.applyform.controller;

import com.cruru.applyform.controller.request.ApplyFormSubmitRequest;
import com.cruru.applyform.controller.request.ApplyFormWriteRequest;
import com.cruru.applyform.controller.response.ApplyFormResponse;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.facade.ApplyFormFacade;
import com.cruru.auth.annotation.RequireAuth;
import com.cruru.auth.annotation.ValidAuth;
import com.cruru.global.LoginProfile;
import jakarta.validation.Valid;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    private static final DateTimeFormatter CSV_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String CSV_FILE_SUFFIX = "_applicant.csv";

    @PostMapping("/{applyformId}/submit")
    public ResponseEntity<Void> submit(
            @RequestBody @Valid ApplyFormSubmitRequest request,
            @PathVariable("applyformId") Long applyFormId
    ) {
        applyFormFacade.submit(applyFormId, request);
        return ResponseEntity.created(URI.create("/v1/applyform/" + applyFormId)).build();
    }

    @GetMapping("/{applyformId}")
    public ResponseEntity<ApplyFormResponse> read(@PathVariable("applyformId") Long applyFormId) {
        ApplyFormResponse response = applyFormFacade.readApplyFormById(applyFormId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{applyformId}/export-csv")
    @ValidAuth
    public ResponseEntity<Resource> exportApplicantsToCsv(
            @RequireAuth(targetDomain = ApplyForm.class) @PathVariable("applyformId") Long applyFormId,
            LoginProfile loginProfile
    ) {
        ByteArrayInputStream csvStream = applyFormFacade.exportApplicantsToCsv(applyFormId);

        String currentDate = LocalDate.now().format(CSV_DATE_FORMAT);
        String fileName = currentDate + CSV_FILE_SUFFIX;

        InputStreamResource resource = new InputStreamResource(csvStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(resource);
    }

    @PatchMapping("/{applyformId}")
    @ValidAuth
    public ResponseEntity<Void> update(
            @RequestBody @Valid ApplyFormWriteRequest request,
            @RequireAuth(targetDomain = ApplyForm.class) @PathVariable("applyformId") Long applyFormId,
            LoginProfile loginProfile
    ) {
        applyFormFacade.update(request, applyFormId);
        return ResponseEntity.ok().build();
    }
}
