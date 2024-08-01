package com.cruru.dashboard.service.facade;

import com.cruru.applyform.controller.dto.ApplyFormCreateRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.service.ApplyFormService;
import com.cruru.dashboard.controller.dto.DashboardCreateRequest;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.service.DashboardService;
import com.cruru.question.controller.dto.QuestionCreateRequest;
import com.cruru.question.service.QuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DashboardFacade {

    private final DashboardService dashboardService;
    private final ApplyFormService applyFormService;
    private final QuestionService questionService;

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
}
