package com.cruru.question.facade;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.service.ApplyFormService;
import com.cruru.question.controller.request.QuestionCreateRequest;
import com.cruru.question.controller.request.QuestionUpdateRequests;
import com.cruru.question.exception.QuestionUnmodifiableException;
import com.cruru.question.service.QuestionService;
import java.time.Clock;
import java.time.LocalDate;
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
    private final Clock clock;

    @Transactional
    public void update(QuestionUpdateRequests request, String applyFormId) {
        ApplyForm applyForm = applyFormService.findByTsid(applyFormId);
        validateRecruitmentStarted(applyForm);
        questionService.deleteAllByApplyForm(applyForm);
        List<QuestionCreateRequest> newQuestions = request.questions();

        newQuestions.forEach(question -> questionService.create(question, applyForm));
    }

    private void validateRecruitmentStarted(ApplyForm applyForm) {
        if (applyForm.hasStarted(LocalDate.now(clock))) {
            throw new QuestionUnmodifiableException();
        }
    }
}
