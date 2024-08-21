package com.cruru.question.service.facade;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.service.ApplyFormService;
import com.cruru.question.controller.dto.QuestionCreateRequest;
import com.cruru.question.controller.dto.QuestionUpdateRequests;
import com.cruru.question.service.QuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionFacade {

    private final ApplyFormService applyFormService;
    private final QuestionService questionService;

    @Transactional
    public void update(QuestionUpdateRequests request, long applyFormId) {
        ApplyForm applyForm = applyFormService.findById(applyFormId);
        questionService.deleteAllByApplyForm(applyForm);
        List<QuestionCreateRequest> newQuestions = request.questions();

        newQuestions.forEach(question -> questionService.create(question, applyForm));
    }
}
