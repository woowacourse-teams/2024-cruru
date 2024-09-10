package com.cruru.dashboard.controller;

import com.cruru.auth.controller.request.LoginProfile;
import com.cruru.dashboard.controller.dto.DashboardCreateRequest;
import com.cruru.dashboard.controller.dto.DashboardCreateResponse;
import com.cruru.dashboard.controller.dto.DashboardsOfClubResponse;
import com.cruru.dashboard.service.facade.DashboardFacade;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping
    public ResponseEntity<DashboardCreateResponse> create(
            @RequestParam(name = "clubId") Long clubId,
            @RequestBody @Valid DashboardCreateRequest request,
            LoginProfile loginProfile
    ) {

        DashboardCreateResponse dashboardCreateResponse = dashboardFacade.create(loginProfile, clubId, request);
        return ResponseEntity.created(URI.create("/v1/dashboards/" + dashboardCreateResponse.dashboardId()))
                .body(dashboardCreateResponse);
    }

    @GetMapping
    public ResponseEntity<DashboardsOfClubResponse> readDashboards(
            LoginProfile loginProfile, @RequestParam(name = "clubId") Long clubId) {
        DashboardsOfClubResponse dashboards = dashboardFacade.findAllDashboardsByClubId(loginProfile, clubId);
        return ResponseEntity.ok().body(dashboards);
    }
}
