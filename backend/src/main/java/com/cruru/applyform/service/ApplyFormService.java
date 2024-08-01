package com.cruru.applyform.service;

import com.cruru.applyform.controller.dto.ApplyFormCreateRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.dashboard.domain.Dashboard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyFormService {

    private static final String APPLY_FORM_BASE_URL = "www.cruru.kr/applyform/";

    private final ApplyFormRepository applyFormRepository;

    @Transactional
    public ApplyForm create(ApplyFormCreateRequest request, Dashboard createdDashboard) {
        ApplyForm applyForm = toApplyForm(request, createdDashboard);

        ApplyForm savedApplyForm = applyFormRepository.save(applyForm);
        Long savedId = savedApplyForm.getId();
        String generatedUrl = APPLY_FORM_BASE_URL + savedId;
        savedApplyForm.setUrl(generatedUrl);

        return savedApplyForm;
    }

    public ApplyForm toApplyForm(ApplyFormCreateRequest request, Dashboard createdDashboard) {
        return new ApplyForm(
                request.title(),
                request.postingContent(),
                request.startDate(),
                request.dueDate(),
                createdDashboard
        );
    }
}
