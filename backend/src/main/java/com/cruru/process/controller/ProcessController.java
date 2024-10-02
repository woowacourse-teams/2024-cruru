package com.cruru.process.controller;

import com.cruru.auth.annotation.RequireAuthCheck;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.global.LoginProfile;
import com.cruru.process.controller.request.ProcessCreateRequest;
import com.cruru.process.controller.request.ProcessUpdateRequest;
import com.cruru.process.controller.response.ProcessResponse;
import com.cruru.process.controller.response.ProcessResponses;
import com.cruru.applicant.domain.EvaluationStatus;
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

    @RequireAuthCheck(targetId = "dashboardId", targetDomain = Dashboard.class)
    @GetMapping
    public ResponseEntity<ProcessResponses> read(
            LoginProfile loginProfile,
            @RequestParam(name = "dashboardId") Long dashboardId,
            @RequestParam(name = "minScore", required = false, defaultValue = "0.00") Double minScore,
            @RequestParam(name = "maxScore", required = false, defaultValue = "5.00") Double maxScore,
            @RequestParam(name = "evaluationExists", required = false, defaultValue = "ALL") EvaluationStatus evaluationExists,
            @RequestParam(name = "sortByCreatedAt", required = false, defaultValue = "desc") String sortByCreatedAt,
            @RequestParam(name = "sortByScore", required = false, defaultValue = "desc") String sortByScore
    ) {
        ProcessResponses processes = processFacade.readAllByDashboardId(
                dashboardId, minScore, maxScore, evaluationExists, sortByCreatedAt, sortByScore);
        return ResponseEntity.ok().body(processes);
    }

    @RequireAuthCheck(targetId = "dashboardId", targetDomain = Dashboard.class)
    @PostMapping
    public ResponseEntity<Void> create(
            LoginProfile loginProfile,
            @RequestParam(name = "dashboardId") Long dashboardId,
            @RequestBody @Valid ProcessCreateRequest request
    ) {
        processFacade.create(request, dashboardId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequireAuthCheck(targetId = "processId", targetDomain = Process.class)
    @PatchMapping("/{processId}")
    public ResponseEntity<ProcessResponse> update(
            LoginProfile loginProfile,
            @PathVariable Long processId,
            @RequestBody @Valid ProcessUpdateRequest request
    ) {
        ProcessResponse response = processFacade.update(request, processId);
        return ResponseEntity.ok().body(response);
    }

    @RequireAuthCheck(targetId = "processId", targetDomain = Process.class)
    @DeleteMapping("/{processId}")
    public ResponseEntity<Void> delete(LoginProfile loginProfile, @PathVariable Long processId) {
        processFacade.delete(processId);
        return ResponseEntity.noContent().build();
    }
}
