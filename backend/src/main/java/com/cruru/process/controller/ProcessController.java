package com.cruru.process.controller;

import com.cruru.auth.annotation.RequireAuth;
import com.cruru.auth.annotation.ValidAuth;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.global.LoginProfile;
import com.cruru.process.controller.request.ProcessCreateRequest;
import com.cruru.process.controller.request.ProcessUpdateRequest;
import com.cruru.process.controller.response.ProcessResponse;
import com.cruru.process.controller.response.ProcessResponses;
import com.cruru.process.domain.Process;
import com.cruru.process.facade.ProcessFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/processes")
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessFacade processFacade;

    @GetMapping
    @ValidAuth
    public ResponseEntity<ProcessResponses> read(
            @RequireAuth(targetDomain = Dashboard.class) @RequestParam(name = "dashboardId") Long dashboardId,
            @RequestParam(name = "minScore", required = false, defaultValue = "0.00") Double minScore,
            @RequestParam(name = "maxScore", required = false, defaultValue = "5.00") Double maxScore,
            @RequestParam(name = "evaluationStatus", required = false, defaultValue = "ALL") String evaluationStatus,
            @RequestParam(name = "sortByCreatedAt", required = false) String sortByCreatedAt,
            @RequestParam(name = "sortByScore", required = false) String sortByScore,
            LoginProfile loginProfile
    ) {
        ProcessResponses processes = processFacade.readAllByDashboardId(
                dashboardId, minScore, maxScore, evaluationStatus, sortByCreatedAt, sortByScore
        );
        return ResponseEntity.ok().body(processes);
    }

    @PostMapping
    @ValidAuth
    public ResponseEntity<Void> create(
            @RequireAuth(targetDomain = Dashboard.class) @RequestParam(name = "dashboardId") Long dashboardId,
            @RequestBody @Valid ProcessCreateRequest request,
            LoginProfile loginProfile
    ) {
        processFacade.create(request, dashboardId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{processId}")
    @ValidAuth
    public ResponseEntity<ProcessResponse> update(
            @RequireAuth(targetDomain = Process.class) @PathVariable Long processId,
            @RequestBody @Valid ProcessUpdateRequest request,
            LoginProfile loginProfile
    ) {
        ProcessResponse response = processFacade.update(request, processId);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{processId}")
    @ValidAuth
    public ResponseEntity<Void> delete(
            @RequireAuth(targetDomain = Process.class) @PathVariable Long processId,
            LoginProfile loginProfile
    ) {
        processFacade.delete(processId);
        return ResponseEntity.noContent().build();
    }
}
