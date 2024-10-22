package com.cruru.applyform.service;

import com.cruru.applyform.controller.request.ApplyFormWriteRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.applyform.exception.ApplyFormNotFoundException;
import com.cruru.applyform.exception.badrequest.StartDatePastException;
import com.cruru.dashboard.domain.Dashboard;
import java.time.Clock;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyFormService {

    private final ApplyFormRepository applyFormRepository;
    private final Clock clock;

    @Transactional
    public ApplyForm create(ApplyFormWriteRequest request, Dashboard createdDashboard) {
        ApplyForm applyForm = toApplyForm(request, createdDashboard);
        validateStartDateNotInPast(applyForm);

        return applyFormRepository.save(applyForm);
    }

    private ApplyForm toApplyForm(ApplyFormWriteRequest request, Dashboard createdDashboard) {
        return new ApplyForm(
                request.title(),
                request.postingContent(),
                request.startDate(),
                request.endDate(),
                createdDashboard
        );
    }

    private void validateStartDateNotInPast(ApplyForm applyForm) {
        LocalDate startDate = applyForm.getStartDate().toLocalDate();
        LocalDate now = LocalDate.now(clock);
        if (startDate.isBefore(now)) {
            throw new StartDatePastException(startDate, now);
        }
    }

    @Transactional
    public void update(ApplyForm targetApplyForm, ApplyFormWriteRequest updateRequest) {
        if (changeExists(targetApplyForm, updateRequest)) {
            applyFormRepository.save(toUpdateApplyForm(targetApplyForm, updateRequest));
        }
    }

    private boolean changeExists(ApplyForm applyForm, ApplyFormWriteRequest updateRequest) {
        return !(applyForm.getTitle().equals(updateRequest.title()) &&
                applyForm.getDescription().equals(updateRequest.postingContent()) &&
                applyForm.getStartDate().equals(updateRequest.startDate()) &&
                applyForm.getEndDate().equals(updateRequest.endDate())
        );
    }

    private ApplyForm toUpdateApplyForm(ApplyForm targetApplyForm, ApplyFormWriteRequest updateRequest) {
        return new ApplyForm(
                targetApplyForm.getId(),
                updateRequest.title(),
                updateRequest.postingContent(),
                updateRequest.startDate(),
                updateRequest.endDate(),
                targetApplyForm.getDashboard()
        );
    }

    public ApplyForm findByTsid(String applyFormId) {
        return applyFormRepository.findByTsid(applyFormId)
                .orElseThrow(ApplyFormNotFoundException::new);
    }

    public ApplyForm findByDashboardId(Long dashboardId) {
        return applyFormRepository.findByDashboardId(dashboardId)
                .orElseThrow(ApplyFormNotFoundException::new);
    }

    public ApplyForm findByDashboard(Dashboard dashboard) {
        return applyFormRepository.findByDashboard(dashboard)
                .orElseThrow(ApplyFormNotFoundException::new);
    }

    @Transactional
    public void delete(ApplyForm applyForm) {
        applyFormRepository.delete(applyForm);
    }
}
