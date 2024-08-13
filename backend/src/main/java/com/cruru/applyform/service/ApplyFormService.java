package com.cruru.applyform.service;

import com.cruru.applyform.controller.dto.ApplyFormCreateRequest;
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

    @Value("${base-url.applyform}")
    private String applyPostBaseUrl;

    @Transactional
    public ApplyForm create(ApplyFormCreateRequest request, Dashboard createdDashboard) {
        ApplyForm applyForm = toApplyForm(request, createdDashboard);

        ApplyForm savedApplyForm = applyFormRepository.save(applyForm);
        Long savedPostingId = savedApplyForm.getId();
        String generatedUrl = String.format(applyPostBaseUrl, savedPostingId);
        savedApplyForm.setUrl(generatedUrl);

        return savedApplyForm;
    }

    private ApplyForm toApplyForm(ApplyFormCreateRequest request, Dashboard createdDashboard) {
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
}
