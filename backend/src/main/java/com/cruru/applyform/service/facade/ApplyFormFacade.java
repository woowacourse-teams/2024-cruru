package com.cruru.applyform.service.facade;

import com.cruru.answer.service.AnswerService;
import com.cruru.applicant.controller.dto.ApplicantCreateRequest;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.applyform.controller.dto.AnswerCreateRequest;
import com.cruru.applyform.controller.dto.ApplyFormResponse;
import com.cruru.applyform.controller.dto.ApplyFormSubmitRequest;
import com.cruru.applyform.controller.dto.ApplyFormWriteRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.exception.badrequest.ApplyFormSubmitOutOfPeriodException;
import com.cruru.applyform.exception.badrequest.PersonalDataCollectDisagreeException;
import com.cruru.applyform.service.ApplyFormService;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.domain.Process;
import com.cruru.process.service.ProcessService;
import com.cruru.question.domain.Question;
import com.cruru.question.service.QuestionService;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private void validateSubmitDate(ApplyForm applyForm) {
        LocalDate now = LocalDate.now(clock);
        // 추후 날짜가 아닌 시간까지 검증하는 경우 수정 필요
        LocalDate startDate = applyForm.getStartDate().toLocalDate();
        LocalDate endDate = applyForm.getEndDate().toLocalDate();
        if (now.isBefore(startDate) || now.isAfter(endDate)) {
            throw new ApplyFormSubmitOutOfPeriodException();
        }
    }

    @Transactional
    public void update(ApplyFormWriteRequest request, long applyFormId) {
        ApplyForm applyForm = applyFormService.findById(applyFormId);

        if (changeExists(request, applyForm)) {
            ApplyForm toUpdateApplyForm = toUpdateApplyForm(request, applyForm, applyFormId);
            applyFormService.update(toUpdateApplyForm);
        }
    }

    private boolean changeExists(ApplyFormWriteRequest request, ApplyForm applyForm) {
        return !(applyForm.getTitle().equals(request.title()) &&
                applyForm.getDescription().equals(request.postingContent()) &&
                applyForm.getStartDate().equals(request.startDate()) &&
                applyForm.getEndDate().equals(request.endDate())
        );
    }

    private static ApplyForm toUpdateApplyForm(
            ApplyFormWriteRequest request,
            ApplyForm applyForm,
            long applyFormId
    ) {
        return new ApplyForm(
                applyFormId,
                request.title(),
                request.postingContent(),
                applyForm.getUrl(),
                request.startDate(),
                request.endDate(),
                applyForm.getDashboard()
        );
    }
}
