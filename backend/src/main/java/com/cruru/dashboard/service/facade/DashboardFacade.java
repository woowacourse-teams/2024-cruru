package com.cruru.dashboard.service.facade;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.applyform.controller.dto.ApplyFormCreateRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.service.ApplyFormService;
import com.cruru.club.service.ClubService;
import com.cruru.dashboard.controller.dto.DashboardCreateRequest;
import com.cruru.dashboard.controller.dto.DashboardPreviewResponse;
import com.cruru.dashboard.controller.dto.DashboardsOfClubResponse;
import com.cruru.dashboard.controller.dto.StatsResponse;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.service.DashboardService;
import com.cruru.process.domain.Process;
import com.cruru.process.service.ProcessService;
import com.cruru.question.service.QuestionService;
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
        Dashboard createdDashboard = dashboardService.create(clubId);
        ApplyForm applyForm = applyFormService.create(toApplyFormCreateRequest(request), createdDashboard);
        questionService.createAll(request.questions(), applyForm);
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

    public DashboardsOfClubResponse findAllDashboardsByClubId(Long clubId) {
        List<Long> dashboardIds = getDashboardIdsByClubId(clubId);
        String clubName = clubService.findById(clubId).getName();
        List<DashboardPreviewResponse> dashboardResponses = dashboardIds.stream()
                .map(this::createDashboardPreviewResponse)
                .sorted(Comparator.comparing(DashboardPreviewResponse::endDate))
                .toList();
        return new DashboardsOfClubResponse(clubName, dashboardResponses);
    }

    private List<Long> getDashboardIdsByClubId(Long clubId) {
        return dashboardService.findAllByClubId(clubId)
                .stream()
                .map(Dashboard::getId)
                .toList();
    }

    private DashboardPreviewResponse createDashboardPreviewResponse(Long dashboardId) {
        ApplyForm applyForm = applyFormService.findByDashboardId(dashboardId);
        List<Applicant> allApplicants = getAllApplicantsByDashboardId(dashboardId);
        StatsResponse stats = calculateStats(allApplicants);
        return new DashboardPreviewResponse(
                dashboardId,
                applyForm.getTitle(),
                stats,
                applyForm.getUrl(),
                applyForm.getDueDate()
        );
    }

    private List<Applicant> getAllApplicantsByDashboardId(Long dashboardId) {
        List<Process> processes = processService.findAllByDashboardId(dashboardId);
        return processes.stream()
                .flatMap(process -> applicantService.findAllByProcess(process)
                        .stream())
                .toList();
    }

    private StatsResponse calculateStats(List<Applicant> allApplicants) {
        int totalApplicants = allApplicants.size();
        int totalFails = (int) allApplicants.stream()
                .filter(Applicant::isRejected)
                .count();
        int totalAccepts = (int) allApplicants.stream()
                .filter(Applicant::isApproved)
                .count();
        int totalPending = (int) allApplicants.stream()
                .filter(Applicant::isPending)
                .count();
        return new StatsResponse(totalAccepts, totalFails, totalPending, totalApplicants);
    }
}
