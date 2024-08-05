package com.cruru.applyform.service;

import com.cruru.advice.InternalServerException;
import com.cruru.answer.domain.Answer;
import com.cruru.answer.domain.repository.AnswerRepository;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.controller.dto.AnswerCreateRequest;
import com.cruru.applyform.controller.dto.ApplyFormCreateRequest;
import com.cruru.applyform.controller.dto.ApplyFormResponse;
import com.cruru.applyform.controller.dto.ApplyFormSubmitRequest;
import com.cruru.applyform.controller.dto.ChoiceResponse;
import com.cruru.applyform.controller.dto.QuestionResponse;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.applyform.exception.ApplyFormNotFoundException;
import com.cruru.applyform.exception.badrequest.PersonalDataProcessingException;
import com.cruru.choice.domain.Choice;
import com.cruru.choice.domain.repository.ChoiceRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.question.exception.QuestionNotFoundException;
import java.util.Collections;
import java.util.List;
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
    private final ChoiceRepository choiceRepository;

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
                request.endDate(),
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
                        firstProcess,
                        false
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
            Question question = questionRepository.findById(answerCreateRequest.questionId())
                    .orElseThrow(QuestionNotFoundException::new);
            Answer answer = new Answer(reply, question, applicant);
            answerRepository.save(answer);
        }
    }

    public ApplyFormResponse read(long applyFormId) {
        ApplyForm applyForm = applyFormRepository.findById(applyFormId)
                .orElseThrow(ApplyFormNotFoundException::new);

        List<QuestionResponse> questionResponses = questionRepository.findAllByApplyFormId(applyFormId)
                .stream()
                .map(this::toQuestionResponse)
                .toList();

        return new ApplyFormResponse(
                applyForm.getTitle(),
                applyForm.getDescription(),
                applyForm.getStartDate(),
                applyForm.getEndDate(),
                questionResponses
        );
    }

    private QuestionResponse toQuestionResponse(Question question) {
        List<Choice> choices = getChoices(question);
        return new QuestionResponse(
                question.getId(),
                question.getQuestionType().name(),
                question.getContent(),
                question.getDescription(),
                question.getSequence(),
                toChoiceResponses(choices)
        );
    }

    private List<Choice> getChoices(Question question) {
        if (question.hasChoice()) {
            return choiceRepository.findAllByQuestionId(question.getId());
        }
        return Collections.emptyList();
    }

    private List<ChoiceResponse> toChoiceResponses(List<Choice> choices) {
        return choices.stream()
                .map(this::toChoiceResponse)
                .toList();
    }

    private ChoiceResponse toChoiceResponse(Choice choice) {
        return new ChoiceResponse(choice.getId(), choice.getContent(), choice.getSequence());
    }
}
