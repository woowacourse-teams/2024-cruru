package com.cruru.applyform.service;

import com.cruru.applyform.controller.dto.ApplyFormWriteRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.applyform.exception.ApplyFormNotFoundException;
import com.cruru.applyform.exception.badrequest.StartDatePastException;
import com.cruru.dashboard.domain.Dashboard;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyFormService {

    private final ApplyFormRepository applyFormRepository;

    @Value("${base-url.applyform}")
    private String applyPostBaseUrl;

    @Transactional
    public ApplyForm create(ApplyFormWriteRequest request, Dashboard createdDashboard) {
        ApplyForm applyForm = toApplyForm(request, createdDashboard);
        validateStartDateNotInPast(applyForm);

        ApplyForm savedApplyForm = applyFormRepository.save(applyForm);
        Long savedPostingId = savedApplyForm.getId();
        String generatedUrl = String.format(applyPostBaseUrl, savedPostingId);
        savedApplyForm.setUrl(generatedUrl);

        return savedApplyForm;
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
        if (startDate.isBefore(LocalDate.now())) {
            throw new StartDatePastException(startDate, LocalDate.now());
        }
    }

    @Transactional
    public void update(ApplyForm applyForm) {
        validateStartDateNotInPast(applyForm);
        applyFormRepository.save(applyForm);
    }

    public ApplyForm findById(long applyFormId) {
        return applyFormRepository.findById(applyFormId)
                .orElseThrow(ApplyFormNotFoundException::new);
    }

    public ApplyForm findByDashboard(Dashboard dashboard) {
        return applyFormRepository.findByDashboard(dashboard)
                .orElseThrow(ApplyFormNotFoundException::new);
    }
}
