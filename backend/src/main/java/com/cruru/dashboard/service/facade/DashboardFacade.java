package com.cruru.dashboard.service.facade;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.applyform.controller.request.ApplyFormWriteRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.service.ApplyFormService;
import com.cruru.auth.controller.request.LoginProfile;
import com.cruru.auth.util.AuthChecker;
import com.cruru.club.domain.Club;
import com.cruru.club.service.ClubService;
import com.cruru.dashboard.controller.dto.DashboardCreateRequest;
import com.cruru.dashboard.controller.dto.DashboardCreateResponse;
import com.cruru.dashboard.controller.dto.DashboardPreviewResponse;
import com.cruru.dashboard.controller.dto.DashboardsOfClubResponse;
import com.cruru.dashboard.controller.dto.StatsResponse;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.service.DashboardService;
import com.cruru.member.domain.Member;
import com.cruru.member.service.MemberService;
import com.cruru.process.domain.Process;
import com.cruru.process.service.ProcessService;
import com.cruru.question.controller.request.QuestionCreateRequest;
import com.cruru.question.service.QuestionService;
import java.time.Clock;
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

    private final MemberService memberService;
    private final ClubService clubService;
    private final DashboardService dashboardService;
    private final ApplyFormService applyFormService;
    private final QuestionService questionService;
    private final ProcessService processService;
    private final ApplicantService applicantService;
    private final Clock clock;

    @Transactional
    public DashboardCreateResponse create(LoginProfile loginProfile, long clubId, DashboardCreateRequest request) {
        Member member = memberService.findByEmail(loginProfile.email());
        Club club = clubService.findById(clubId);

        AuthChecker.checkAuthority(club, member);

        Dashboard dashboard = dashboardService.create(club);
        ApplyForm applyForm = applyFormService.create(toApplyFormWriteRequest(request), dashboard);
        for (QuestionCreateRequest questionCreateRequest : request.questions()) {
            questionService.create(questionCreateRequest, applyForm);
        }
        return new DashboardCreateResponse(applyForm.getId(), dashboard.getId());
    }

    private ApplyFormWriteRequest toApplyFormWriteRequest(DashboardCreateRequest request) {
        return new ApplyFormWriteRequest(
                request.title(),
                request.postingContent(),
                request.startDate(),
                request.endDate()
        );
    }

    public DashboardsOfClubResponse findAllDashboardsByClubId(LoginProfile loginProfile, long clubId) {
        Member member = memberService.findByEmail(loginProfile.email());
        Club club = clubService.findById(clubId);

        AuthChecker.checkAuthority(club, member);

        List<Dashboard> dashboards = dashboardService.findAllByClub(club);

        String clubName = clubService.findById(clubId).getName();
        LocalDateTime now = LocalDateTime.now(clock);
        List<DashboardPreviewResponse> dashboardResponses = dashboards.stream()
                .map(this::createDashboardPreviewResponse)
                .toList();

        List<DashboardPreviewResponse> sortedDashboardPreviews = sortDashboardPreviews(dashboardResponses, now);

        return new DashboardsOfClubResponse(clubName, sortedDashboardPreviews);
    }

    private DashboardPreviewResponse createDashboardPreviewResponse(Dashboard dashboard) {
        ApplyForm applyForm = applyFormService.findByDashboard(dashboard);
        List<Applicant> allApplicants = getAllApplicantsByDashboardId(dashboard);
        StatsResponse stats = calculateStats(allApplicants);
        return new DashboardPreviewResponse(
                dashboard.getId(),
                applyForm.getId(),
                applyForm.getTitle(),
                stats,
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
