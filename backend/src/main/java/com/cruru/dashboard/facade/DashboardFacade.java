package com.cruru.dashboard.facade;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.applicant.service.EvaluationService;
import com.cruru.applyform.controller.request.ApplyFormWriteRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.service.ApplyFormService;
import com.cruru.club.domain.Club;
import com.cruru.club.service.ClubService;
import com.cruru.dashboard.controller.request.DashboardCreateRequest;
import com.cruru.dashboard.controller.response.DashboardCreateResponse;
import com.cruru.dashboard.controller.response.DashboardPreviewResponse;
import com.cruru.dashboard.controller.response.DashboardsOfClubResponse;
import com.cruru.dashboard.controller.response.StatsResponse;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.DashboardApplyFormDto;
import com.cruru.dashboard.service.DashboardService;
import com.cruru.email.service.EmailService;
import com.cruru.process.domain.Process;
import com.cruru.process.service.ProcessService;
import com.cruru.question.controller.request.QuestionCreateRequest;
import com.cruru.question.domain.Question;
import com.cruru.question.service.AnswerService;
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

    private final ClubService clubService;
    private final DashboardService dashboardService;
    private final ApplyFormService applyFormService;
    private final QuestionService questionService;
    private final Clock clock;
    private final ProcessService processService;
    private final ApplicantService applicantService;
    private final AnswerService answerService;
    private final EvaluationService evaluationService;
    private final EmailService emailService;

    @Transactional
    public DashboardCreateResponse create(long clubId, DashboardCreateRequest request) {
        Club club = clubService.findById(clubId);

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

    public DashboardsOfClubResponse findAllDashboardsByClubId(long clubId) {
        List<DashboardApplyFormDto> dashboards = dashboardService.findAllByClub(clubId);

        String clubName = clubService.findById(clubId).getName();
        LocalDateTime now = LocalDateTime.now(clock);
        List<DashboardPreviewResponse> dashboardResponses = dashboards.stream()
                .map(this::createDashboardPreviewResponse)
                .toList();

        List<DashboardPreviewResponse> sortedDashboardPreviews = sortDashboardPreviews(dashboardResponses, now);

        return new DashboardsOfClubResponse(clubName, sortedDashboardPreviews);
    }

    private DashboardPreviewResponse createDashboardPreviewResponse(DashboardApplyFormDto dashboardApplyformDto) {
        Dashboard dashboard = dashboardApplyformDto.dashboard();
        ApplyForm applyForm = dashboardApplyformDto.applyForm();

        List<Applicant> applicants = dashboardService.findAllApplicants(dashboard);
        StatsResponse stats = calculateStats(applicants);
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

    @Transactional
    public void delete(Long dashboardId) {
        Dashboard dashboard = dashboardService.findById(dashboardId);

        ApplyForm applyForm = applyFormService.findByDashboard(dashboard);
        List<Question> questions = questionService.findByApplyForm(applyForm);
        List<Process> processes = processService.findAllByDashboard(dashboardId);
        List<Applicant> applicants = applicantService.findAllByProcesses(processes);

        deleteProcesses(applicants, processes);
        deleteApplyForm(questions, applyForm);
        dashboardService.deleteById(dashboardId);
    }

    private void deleteProcesses(List<Applicant> applicants, List<Process> processes) {
        if (!applicants.isEmpty()) {
            answerService.deleteAllByApplicants(applicants);
            emailService.deleteAllByTos(applicants);
            evaluationService.deleteAllByProcesses(processes);
            applicantService.deleteAllInBatch(applicants);
        }
        processService.deleteAllInBatch(processes);
    }

    private void deleteApplyForm(List<Question> questions, ApplyForm applyForm) {
        if (!questions.isEmpty()) {
            questionService.deleteAllByApplyForm(applyForm);
        }
        applyFormService.delete(applyForm);
    }
}
