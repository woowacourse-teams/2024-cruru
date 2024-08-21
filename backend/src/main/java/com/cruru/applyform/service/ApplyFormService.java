package com.cruru.applyform.service;

import com.cruru.applyform.controller.dto.ApplyFormRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.applyform.exception.ApplyFormNotFoundException;
import com.cruru.dashboard.domain.Dashboard;
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
    public ApplyForm create(ApplyFormRequest request, Dashboard createdDashboard) {
        ApplyForm applyForm = toApplyForm(request, createdDashboard);

        ApplyForm savedApplyForm = applyFormRepository.save(applyForm);
        Long savedPostingId = savedApplyForm.getId();
        String generatedUrl = String.format(applyPostBaseUrl, savedPostingId);
        savedApplyForm.setUrl(generatedUrl);

        return savedApplyForm;
    }

    private ApplyForm toApplyForm(ApplyFormRequest request, Dashboard createdDashboard) {
        return new ApplyForm(
                request.title(),
                request.postingContent(),
                request.startDate(),
                request.endDate(),
                createdDashboard
        );
    }

    public ApplyForm findById(long applyFormId) {
        return applyFormRepository.findById(applyFormId)
                .orElseThrow(ApplyFormNotFoundException::new);
    }

    public ApplyForm findByDashboard(Dashboard dashboard) {
        return applyFormRepository.findByDashboard(dashboard)
                .orElseThrow(ApplyFormNotFoundException::new);
    }

    @Transactional
    public void update(ApplyFormRequest request, long applyFormId) {
        ApplyForm applyForm = findById(applyFormId);
        if (changeExists(request, applyForm)) {
            applyFormRepository.save(
                    new ApplyForm(
                            applyFormId,
                            request.title(),
                            request.postingContent(),
                            applyForm.getUrl(),
                            request.startDate(),
                            request.endDate(),
                            applyForm.getDashboard()
                    )
            );
        }
    }

    private boolean changeExists(ApplyFormRequest request, ApplyForm applyForm) {
        return !(applyForm.getTitle().equals(request.title()) &&
                applyForm.getDescription().equals(request.postingContent()) &&
                applyForm.getStartDate().equals(request.startDate()) &&
                applyForm.getEndDate().equals(request.endDate())
        );
    }
}
