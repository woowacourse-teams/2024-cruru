package com.cruru.dashboard.service.facade;

import com.cruru.applicant.controller.dto.DashboardApplicantResponse;
import com.cruru.applyform.controller.dto.ApplyFormCreateRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.service.ApplyFormService;
import com.cruru.club.service.ClubService;
import com.cruru.dashboard.controller.dto.DashboardCreateRequest;
import com.cruru.dashboard.controller.dto.DashboardPreviewResponse;
import com.cruru.dashboard.controller.dto.DashboardPreviewResponses;
import com.cruru.dashboard.controller.dto.DashboardsOfClubResponse;
import com.cruru.dashboard.controller.dto.Stats;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.service.DashboardService;
import com.cruru.process.controller.dto.ProcessesResponse;
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
                request.dueDate()
        );
    }

    @Transactional
    public DashboardsOfClubResponse findAllDashboardsByClubId(Long clubId) {
        List<Long> dashboardIds = getDashboardIdsByClubId(clubId);
        String clubName = clubService.findById(clubId).getName();
        List<DashboardPreviewResponse> dashboardInfos = dashboardIds.stream()
                .map(this::createDashboardResponse)
                .sorted(Comparator.comparing(DashboardPreviewResponse::endDate))
                .toList();
        DashboardPreviewResponses dashboardResponses = new DashboardPreviewResponses(dashboardInfos);
        return new DashboardsOfClubResponse(clubName, dashboardResponses);
    }

    private List<Long> getDashboardIdsByClubId(Long clubId) {
        return dashboardService.findAllByClubId(clubId)
                .stream()
                .map(Dashboard::getId)
                .toList();
    }

    private DashboardPreviewResponse createDashboardResponse(Long dashboardId) {
        ApplyForm applyForm = applyFormService.findByDashboardId(dashboardId);
        List<DashboardApplicantResponse> allApplicants = getAllApplicantsByDashboardId(dashboardId);
        Stats stats = calculateStats(allApplicants);
        return new DashboardPreviewResponse(
                dashboardId,
                applyForm.getTitle(),
                stats,
                applyForm.getUrl(),
                applyForm.getDueDate()
        );
    }

    private List<DashboardApplicantResponse> getAllApplicantsByDashboardId(Long dashboardId) {
        ProcessesResponse processes = processService.findByDashboardId(dashboardId);
        return processes.processResponses()
                .stream()
                .flatMap(processResponse -> processResponse.dashboardApplicantResponses()
                        .stream())
                .toList();
    }

    private Stats calculateStats(List<DashboardApplicantResponse> allApplicants) {
        int totalApplicants = allApplicants.size();
        int totalFails = (int) allApplicants.stream()
                .filter(DashboardApplicantResponse::isRejected).count();
        int totalAccepts = totalApplicants - totalFails;
        int totalPending = totalApplicants - (totalAccepts + totalFails);
        return new Stats(totalAccepts, totalFails, totalPending, totalApplicants);
    }
}
