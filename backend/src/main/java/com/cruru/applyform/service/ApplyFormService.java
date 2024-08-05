package com.cruru.applyform.service;

import com.cruru.advice.InternalServerException;
import com.cruru.answer.domain.Answer;
import com.cruru.answer.domain.repository.AnswerRepository;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.controller.dto.AnswerCreateRequest;
import com.cruru.applyform.controller.dto.ApplyFormCreateRequest;
import com.cruru.applyform.controller.dto.ApplyFormSubmitRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.applyform.exception.ApplyFormNotFoundException;
import com.cruru.applyform.exception.PersonalDataProcessingException;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.question.exception.QuestionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyFormService {

    private static final String APPLY_FORM_BASE_URL = "www.cruru.kr/applyform/";

    private final ApplicantRepository applicantRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ApplyFormRepository applyFormRepository;
    private final ProcessRepository processRepository;

    @Transactional
    public ApplyForm create(ApplyFormCreateRequest request, Dashboard createdDashboard) {
        ApplyForm applyForm = toApplyForm(request, createdDashboard);

        ApplyForm savedApplyForm = applyFormRepository.save(applyForm);
        Long savedId = savedApplyForm.getId();
        String generatedUrl = APPLY_FORM_BASE_URL + savedId;
        savedApplyForm.setUrl(generatedUrl);

        return savedApplyForm;
    }

    private ApplyForm toApplyForm(ApplyFormCreateRequest request, Dashboard createdDashboard) {
        return new ApplyForm(
                request.title(),
                request.postingContent(),
                request.startDate(),
                request.dueDate(),
                createdDashboard
        );
    }

    @Transactional
    public void submit(ApplyFormSubmitRequest request, long applyFormId) {
        validatePersonalDataCollection(request);

        ApplyForm applyForm = applyFormRepository.findById(applyFormId)
                .orElseThrow(ApplyFormNotFoundException::new);

        Process firstProcess = processRepository.findFirstByDashboardIdOrderBySequenceAsc(
                        applyForm.getDashboard().getId()
                )
                .orElseThrow(InternalServerException::new);

        Applicant applicant = applicantRepository.save(
                new Applicant(
                        request.applicantCreateRequest().name(),
                        request.applicantCreateRequest().email(),
                        request.applicantCreateRequest().phone(),
                        firstProcess
                )
        );

        for (AnswerCreateRequest answerCreateRequest : request.answerCreateRequest()) {
            saveAnswerReplies(answerCreateRequest, applicant);
        }
    }

    private void validatePersonalDataCollection(ApplyFormSubmitRequest request) {
        if (!request.personalDataCollection()) {
            throw new PersonalDataProcessingException();
        }
    }

    private void saveAnswerReplies(AnswerCreateRequest answerCreateRequest, Applicant applicant) {
        for (String reply : answerCreateRequest.replies()) {
            Question question = getQuestionById(answerCreateRequest.questionId());
            Answer answer = new Answer(reply, question, applicant);
            answerRepository.save(answer);
        }
    }

    private Question getQuestionById(long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);
    }

    @Transactional
    public ApplyForm findById(long applyFormId) {
        return applyFormRepository.findById(applyFormId)
                .orElseThrow(ApplyFormNotFoundException::new);
    }

    @Transactional
    public ApplyForm findByDashboardId(long dashboardId) {
        return applyFormRepository.findByDashboardId(dashboardId)
                .orElseThrow(ApplyFormNotFoundException::new);
    }
}
