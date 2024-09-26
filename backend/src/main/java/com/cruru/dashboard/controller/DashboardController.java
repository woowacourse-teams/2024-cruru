package com.cruru.dashboard.controller;

import com.cruru.auth.annotation.RequireAuthCheck;
import com.cruru.club.domain.Club;
import com.cruru.dashboard.controller.request.DashboardCreateRequest;
import com.cruru.dashboard.controller.response.DashboardCreateResponse;
import com.cruru.dashboard.controller.response.DashboardsOfClubResponse;
import com.cruru.dashboard.facade.DashboardFacade;
import com.cruru.global.LoginProfile;
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
    @RequireAuthCheck(targetId = "clubId", targetDomain = Club.class)
    public ResponseEntity<DashboardCreateResponse> create(
            @RequestParam(name = "clubId") Long clubId,
            @RequestBody @Valid DashboardCreateRequest request,
            LoginProfile loginProfile
    ) {

        DashboardCreateResponse dashboardCreateResponse = dashboardFacade.create(clubId, request);
        return ResponseEntity.created(URI.create("/v1/dashboards/" + dashboardCreateResponse.dashboardId()))
                .body(dashboardCreateResponse);
    }

    @GetMapping
    @RequireAuthCheck(targetId = "clubId", targetDomain = Club.class)
    public ResponseEntity<DashboardsOfClubResponse> readDashboards(
            @RequestParam(name = "clubId") Long clubId,
            LoginProfile loginProfile
    ) {
        DashboardsOfClubResponse dashboards = dashboardFacade.findAllDashboardsByClubId(clubId);
        return ResponseEntity.ok().body(dashboards);
    }
}
