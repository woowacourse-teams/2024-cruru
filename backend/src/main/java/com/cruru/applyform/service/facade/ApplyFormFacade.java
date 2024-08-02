package com.cruru.applyform.service.facade;

import com.cruru.applyform.controller.dto.ApplyFormResponse;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.service.ApplyFormService;
import com.cruru.question.controller.dto.QuestionResponse;
import com.cruru.question.service.QuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyFormFacade {

    private final QuestionService questionService;
    private final ApplyFormService applyFormService;

    public ApplyFormResponse findApplyFormById(long applyFormId) {
        ApplyForm applyForm = applyFormService.findById(applyFormId);
        List<QuestionResponse> questionResponses = questionService.findByApplyFormId(applyFormId);

        return new ApplyFormResponse(
                applyForm.getTitle(),
                applyForm.getDescription(),
                applyForm.getOpenDate(),
                applyForm.getDueDate(),
                questionResponses
        );
    }
}
