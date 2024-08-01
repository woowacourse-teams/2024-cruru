package com.cruru.dashboard.controller;

import com.cruru.dashboard.controller.dto.DashboardCreateRequest;
import com.cruru.dashboard.service.DashboardService;
import com.cruru.dashboard.service.facade.DashboardFacade;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dashboards")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardFacade dashboardFacade;
    private final DashboardService dashboardService;

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestParam(name = "club_id") Long clubId,
            @RequestBody @Valid DashboardCreateRequest request) {

        long dashboardId = dashboardFacade.create(clubId, request);
        return ResponseEntity.created(URI.create("/v1/dashboards/" + dashboardId)).build();
    }
}
