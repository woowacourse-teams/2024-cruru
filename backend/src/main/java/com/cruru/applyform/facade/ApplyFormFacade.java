package com.cruru.applyform.facade;

import com.cruru.applicant.controller.request.ApplicantCreateRequest;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.applyform.controller.request.AnswerCreateRequest;
import com.cruru.applyform.controller.request.ApplyFormSubmitRequest;
import com.cruru.applyform.controller.request.ApplyFormWriteRequest;
import com.cruru.applyform.controller.response.ApplyFormResponse;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.exception.badrequest.ApplyFormSubmitOutOfPeriodException;
import com.cruru.applyform.exception.badrequest.PersonalDataCollectDisagreeException;
import com.cruru.applyform.service.ApplyFormService;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.domain.Process;
import com.cruru.process.service.ProcessService;
import com.cruru.question.domain.Question;
import com.cruru.question.service.AnswerService;
import com.cruru.question.service.QuestionService;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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
    private final Clock clock;

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
        validateSubmitDate(applyForm);
        Dashboard dashboard = applyForm.getDashboard();
        Process firstProcess = processService.findApplyProcessOnDashboard(dashboard);

        ApplicantCreateRequest applicantCreateRequest = request.applicantCreateRequest();
        Applicant applicant = applicantService.create(applicantCreateRequest, firstProcess);

        List<AnswerCreateRequest> answerCreateRequests = request.answerCreateRequest();
        List<Question> questions = questionService.findByApplyForm(applyForm);

        for (Question question : questions) {
            AnswerCreateRequest answerCreateRequest = getAnswerCreateRequest(question, answerCreateRequests);
            answerService.saveAnswerReplies(answerCreateRequest, question, applicant);
        }
    }

    private void validatePersonalDataCollection(ApplyFormSubmitRequest request) {
        if (!request.personalDataCollection()) {
            throw new PersonalDataCollectDisagreeException();
        }
    }

    private void validateSubmitDate(ApplyForm applyForm) {
        LocalDate now = LocalDate.now(clock);
        // 추후 날짜가 아닌 시간까지 검증하는 경우 수정 필요
        LocalDate startDate = applyForm.getStartDate().toLocalDate();
        LocalDate endDate = applyForm.getEndDate().toLocalDate();
        if (now.isBefore(startDate) || now.isAfter(endDate)) {
            throw new ApplyFormSubmitOutOfPeriodException();
        }
    }

    private AnswerCreateRequest getAnswerCreateRequest(
            Question question,
            List<AnswerCreateRequest> answerCreateRequests
    ) {
        return answerCreateRequests.stream()
                .filter(answerCreateRequest -> Objects.equals(answerCreateRequest.questionId(), question.getId()))
                .findAny()
                .orElseGet(() -> new AnswerCreateRequest(question.getId(), List.of()));
    }

    @Transactional
    public void update(ApplyFormWriteRequest request, long applyFormId) {
        ApplyForm updateTargetApplyForm = applyFormService.findById(applyFormId);
        applyFormService.update(updateTargetApplyForm, request);
    }
}
