package com.cruru.applicant.service;

import com.cruru.answer.domain.Answer;
import com.cruru.answer.domain.repository.AnswerRepository;
import com.cruru.applicant.controller.dto.ApplicantBasicResponse;
import com.cruru.applicant.controller.dto.ApplicantDetailResponse;
import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.controller.dto.ApplicantResponse;
import com.cruru.applicant.controller.dto.ApplicantUpdateRequest;
import com.cruru.applicant.controller.dto.QnaResponse;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applicant.exception.ApplicantNotFoundException;
import com.cruru.applicant.exception.badrequest.ApplicantRejectException;
import com.cruru.process.controller.dto.ProcessSimpleResponse;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.process.exception.ProcessNotFoundException;
import com.cruru.question.domain.Question;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ProcessRepository processRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public void updateApplicantProcess(long processId, ApplicantMoveRequest moveRequest) {
        Process process = processRepository.findById(processId)
                .orElseThrow(ProcessNotFoundException::new);

        List<Applicant> applicants = applicantRepository.findAllById(moveRequest.applicantIds());
        applicants.forEach(applicant -> applicant.updateProcess(process));
    }

    public List<Applicant> findAllByProcess(Process process) {
        return applicantRepository.findAllByProcess(process);
    }

    public ApplicantBasicResponse findById(long id) {
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(ApplicantNotFoundException::new);
        return toApplicantBasicResponse(applicant);
    }

    private ApplicantBasicResponse toApplicantBasicResponse(Applicant applicant) {
        return new ApplicantBasicResponse(
                new ApplicantResponse(
                        applicant.getId(),
                        applicant.getName(),
                        applicant.getEmail(),
                        applicant.getPhone(),
                        applicant.getCreatedDate()
                ),
                new ProcessSimpleResponse(
                        applicant.getProcess().getId(),
                        applicant.getProcess().getName()
                )
        );
    }

    public ApplicantDetailResponse findDetailById(long id) {
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(ApplicantNotFoundException::new);
        List<Answer> answers = answerRepository.findAllByApplicant(applicant);
        List<QnaResponse> qnaResponses = toQnaResponses(answers);
        return new ApplicantDetailResponse(qnaResponses);
    }

    private List<QnaResponse> toQnaResponses(List<Answer> answers) {
        return answers.stream()
                .map(this::toQnaResponse)
                .toList();
    }

    private QnaResponse toQnaResponse(Answer answer) {
        Question question = answer.getQuestion();
        return new QnaResponse(question.getSequence(), question.getContent(), answer.getContent());
    }

    @Transactional
    public void reject(long id) {
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(ApplicantNotFoundException::new);
        validateRejectable(applicant);
        applicant.reject();
    }

    private void validateRejectable(Applicant applicant) {
        if (applicant.isRejected()) {
            throw new ApplicantRejectException();
        }
    }

    @Transactional
    public void update(ApplicantUpdateRequest request, long applicantId) {
        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(ApplicantNotFoundException::new);

        applicant.updateInfo(request.name(), request.email(), request.phone());
    }
}
