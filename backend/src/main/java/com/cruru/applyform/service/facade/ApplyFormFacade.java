package com.cruru.applyform.service.facade;

import com.cruru.answer.service.AnswerService;
import com.cruru.applicant.controller.dto.ApplicantCreateRequest;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.applyform.controller.dto.AnswerCreateRequest;
import com.cruru.applyform.controller.dto.ApplyFormResponse;
import com.cruru.applyform.controller.dto.ApplyFormSubmitRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.exception.badrequest.PersonalDataCollectDisagreeException;
import com.cruru.applyform.service.ApplyFormService;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.domain.Process;
import com.cruru.process.service.ProcessService;
import com.cruru.question.domain.Question;
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
    private final ProcessService processService;
    private final ApplicantService applicantService;
    private final AnswerService answerService;

    public ApplyFormResponse readApplyFormById(long applyFormId) {
        ApplyForm applyForm = applyFormService.findById(applyFormId);
        List<Question> questions = questionService.findByApplyForm(applyForm);

        return new ApplyFormResponse(
                applyForm.getTitle(),
                applyForm.getDescription(),
                applyForm.getStartDate(),
                applyForm.getEndDate(),
                questionService.toQuestionResponses(questions)
        );
    }

    @Transactional
    public void submit(long applyFormId, ApplyFormSubmitRequest request) {
        validatePersonalDataCollection(request);
        ApplyForm applyForm = applyFormService.findById(applyFormId);
        Dashboard dashboard = applyForm.getDashboard();
        Process firstProcess = processService.findApplyProcessOnDashboard(dashboard);

        ApplicantCreateRequest applicantCreateRequest = request.applicantCreateRequest();
        Applicant applicant = applicantService.create(applicantCreateRequest, firstProcess);

        List<AnswerCreateRequest> answerCreateRequests = request.answerCreateRequest();
        for (AnswerCreateRequest answerCreateRequest : answerCreateRequests) {
            Question targetQuestion = questionService.findById(answerCreateRequest.questionId());
            answerService.saveAnswerReplies(answerCreateRequest, targetQuestion, applicant);
        }
    }

    private void validatePersonalDataCollection(ApplyFormSubmitRequest request) {
        if (!request.personalDataCollection()) {
            throw new PersonalDataCollectDisagreeException();
        }
    }
}
