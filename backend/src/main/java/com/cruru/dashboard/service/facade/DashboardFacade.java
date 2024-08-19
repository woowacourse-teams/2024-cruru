package com.cruru.dashboard.service.facade;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.applyform.controller.dto.ApplyFormCreateRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.service.ApplyFormService;
import com.cruru.club.domain.Club;
import com.cruru.club.service.ClubService;
import com.cruru.dashboard.controller.dto.ApplyFormUrlResponse;
import com.cruru.dashboard.controller.dto.DashboardCreateRequest;
import com.cruru.dashboard.controller.dto.DashboardPreviewResponse;
import com.cruru.dashboard.controller.dto.DashboardsOfClubResponse;
import com.cruru.dashboard.controller.dto.StatsResponse;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.service.DashboardService;
import com.cruru.process.domain.Process;
import com.cruru.process.service.ProcessService;
import com.cruru.question.controller.dto.QuestionCreateRequest;
import com.cruru.question.service.QuestionService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DashboardFacade {

    private final ClubService clubService;
    private final DashboardService dashboardService;
    private final ApplyFormService applyFormService;
    private final QuestionService questionService;
    private final ProcessService processService;
    private final ApplicantService applicantService;

    @Transactional
    public long create(long clubId, DashboardCreateRequest request) {
        Club club = clubService.findById(clubId);
        Dashboard createdDashboard = dashboardService.create(club);
        ApplyForm applyForm = applyFormService.create(toApplyFormCreateRequest(request), createdDashboard);
        for (QuestionCreateRequest questionCreateRequest : request.questions()) {
            questionService.create(questionCreateRequest, applyForm);
        }
        return createdDashboard.getId();
    }

    private ApplyFormCreateRequest toApplyFormCreateRequest(DashboardCreateRequest request) {
        return new ApplyFormCreateRequest(
                request.title(),
                request.postingContent(),
                request.startDate(),
                request.endDate()
        );
    }

    public ApplyFormUrlResponse findFormUrlByDashboardId(long dashboardId) {
        Dashboard dashboard = dashboardService.findById(dashboardId);
        ApplyForm applyForm = applyFormService.findByDashboard(dashboard);
        return new ApplyFormUrlResponse(applyForm.getId(), applyForm.getUrl());
    }

    public DashboardsOfClubResponse findAllDashboardsByClubId(long clubId) {
        List<Dashboard> dashboardIds = getDashboardIdsByClubId(clubId);
        String clubName = clubService.findById(clubId).getName();
        LocalDateTime now = LocalDateTime.now();
        List<DashboardPreviewResponse> dashboardResponses = dashboardIds.stream()
                .map(this::createDashboardPreviewResponse)
                .toList();

        List<DashboardPreviewResponse> sortedDashboardPreviews = sortDashboardPreviews(dashboardResponses, now);

        return new DashboardsOfClubResponse(clubName, sortedDashboardPreviews);
    }

    private List<Dashboard> getDashboardIdsByClubId(long clubId) {
        Club club = clubService.findById(clubId);
        return dashboardService.findAllByClub(club);
    }

    private DashboardPreviewResponse createDashboardPreviewResponse(Dashboard dashboard) {
        ApplyForm applyForm = applyFormService.findByDashboard(dashboard);
        List<Applicant> allApplicants = getAllApplicantsByDashboardId(dashboard);
        StatsResponse stats = calculateStats(allApplicants);
        return new DashboardPreviewResponse(
                dashboard.getId(),
                applyForm.getTitle(),
                stats,
                applyForm.getUrl(),
                applyForm.getStartDate(),
                applyForm.getEndDate()
        );
    }

    private List<DashboardPreviewResponse> sortDashboardPreviews(
            List<DashboardPreviewResponse> dashboardResponses,
            LocalDateTime currentTime
    ) {
        List<DashboardPreviewResponse> nonExpiredDashboards = dashboardResponses.stream()
                .filter(d -> d.endDate().isAfter(currentTime))
                .sorted(Comparator.comparing(DashboardPreviewResponse::endDate)
                        .thenComparing(DashboardPreviewResponse::startDate))
                .toList();

        List<DashboardPreviewResponse> expiredDashboards = dashboardResponses.stream()
                .filter(d -> d.endDate().isBefore(currentTime))
                .sorted(Comparator.comparing(DashboardPreviewResponse::startDate))
                .toList();

        List<DashboardPreviewResponse> sortedDashboards = new ArrayList<>();
        sortedDashboards.addAll(nonExpiredDashboards);
        sortedDashboards.addAll(expiredDashboards);

        return sortedDashboards;
    }

    private List<Applicant> getAllApplicantsByDashboardId(Dashboard dashboard) {
        List<Process> processes = processService.findAllByDashboard(dashboard);
        return processes.stream()
                .flatMap(process -> applicantService.findAllByProcess(process)
                        .stream())
                .toList();
    }

    private StatsResponse calculateStats(List<Applicant> allApplicants) {
        int totalApplicants = allApplicants.size();
        int totalFails = (int) allApplicants.stream()
                .filter(Applicant::isRejected).count();
        int totalAccepts = (int) allApplicants.stream()
                .filter(Applicant::isNotRejected)
                .filter(Applicant::isApproved)
                .count();
        int totalPending = totalApplicants - (totalFails + totalAccepts);
        return new StatsResponse(totalAccepts, totalFails, totalPending, totalApplicants);
    }
}
