package com.cruru.process.controller;

import com.cruru.process.controller.dto.ProcessesResponse;
import com.cruru.process.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
