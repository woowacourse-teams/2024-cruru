package com.cruru.process.controller;

import com.cruru.process.controller.dto.ProcessCreateRequest;
import com.cruru.process.controller.dto.ProcessResponse;
import com.cruru.process.controller.dto.ProcessResponses;
import com.cruru.process.controller.dto.ProcessUpdateRequest;
import com.cruru.process.service.facade.ProcessFacade;
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
    public ResponseEntity<ProcessResponses> read(@RequestParam(name = "dashboardId") Long dashboardId) {
        ProcessResponses processes = processFacade.readAllByDashboardId(dashboardId);
        return ResponseEntity.ok().body(processes);
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestParam(name = "dashboardId") Long dashboardId,
            @RequestBody @Valid ProcessCreateRequest request
    ) {
        processFacade.create(request, dashboardId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{processId}")
    public ResponseEntity<ProcessResponse> update(
            @PathVariable Long processId,
            @RequestBody @Valid ProcessUpdateRequest request
    ) {
        ProcessResponse response = processFacade.update(request, processId);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{processId}")
    public ResponseEntity<Void> delete(@PathVariable Long processId) {
        processFacade.delete(processId);
        return ResponseEntity.noContent().build();
    }
}
