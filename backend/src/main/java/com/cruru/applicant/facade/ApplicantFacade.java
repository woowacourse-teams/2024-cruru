package com.cruru.applicant.facade;

import com.cruru.applicant.controller.request.ApplicantMoveRequest;
import com.cruru.applicant.controller.request.ApplicantUpdateRequest;
import com.cruru.applicant.controller.request.ApplicantsRejectRequest;
import com.cruru.applicant.controller.response.ApplicantAnswerResponses;
import com.cruru.applicant.controller.response.ApplicantBasicResponse;
import com.cruru.applicant.controller.response.ApplicantResponse;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.process.controller.response.ProcessSimpleResponse;
import com.cruru.process.domain.Process;
import com.cruru.process.service.ProcessService;
import com.cruru.question.controller.response.AnswerResponse;
import com.cruru.question.domain.Answer;
import com.cruru.question.service.AnswerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicantFacade {

    private final ApplicantService applicantService;
    private final ProcessService processService;
    private final AnswerService answerService;

    public ApplicantBasicResponse readBasicById(long applicantId) {
        Applicant applicant = applicantService.findById(applicantId);
        return toApplicantBasicResponse(applicant);
    }

    private ApplicantBasicResponse toApplicantBasicResponse(Applicant applicant) {
        ApplicantResponse applicantResponse = applicantService.toApplicantResponse(applicant);
        ProcessSimpleResponse processResponse = processService.toProcessSimpleResponse(applicant.getProcess());
        return new ApplicantBasicResponse(applicantResponse, processResponse);
    }

    public ApplicantAnswerResponses readDetailById(long applicantId) {
        Applicant applicant = applicantService.findById(applicantId);
        List<Answer> answers = answerService.findAllByApplicantWithQuestions(applicant);
        List<AnswerResponse> answerResponses = answerService.toAnswerResponses(answers);
        return new ApplicantAnswerResponses(answerResponses);
    }

    @Transactional
    public void updateApplicantInformation(long applicantId, ApplicantUpdateRequest request) {
        applicantService.updateApplicantInformation(applicantId, request);
    }

    @Transactional
    public void updateApplicantProcess(long processId, ApplicantMoveRequest moveRequest) {
        Process process = processService.findById(processId);
        applicantService.moveApplicantProcess(process, moveRequest);
    }

    @Transactional
    public void reject(long applicantId) {
        applicantService.reject(applicantId);
    }

    @Transactional
    public void unreject(long applicantId) {
        applicantService.unreject(applicantId);
    }

    @Transactional
    public void reject(ApplicantsRejectRequest request) {
        applicantService.reject(request.applicantIds());
    }

    @Transactional
    public void unreject(ApplicantsRejectRequest request) {
        applicantService.unreject(request.applicantIds());
    }
}
