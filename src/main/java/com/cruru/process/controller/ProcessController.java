package com.cruru.process.controller;

import com.cruru.process.controller.dto.ProcessCreateRequest;
import com.cruru.process.controller.dto.ProcessesResponse;
import com.cruru.process.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/processes")
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessService processService;

    @GetMapping
    public ResponseEntity<ProcessesResponse> read(@RequestParam(name = "dashboard_id") Long dashboardId) {
        ProcessesResponse processes = processService.findByDashboardId(dashboardId);
        return ResponseEntity.ok().body(processes);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestParam(name = "dashboard_id") Long dashboardId,
                                       @RequestBody ProcessCreateRequest request) {
        processService.create(dashboardId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
