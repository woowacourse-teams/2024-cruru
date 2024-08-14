package com.cruru.applicant.service.facade;

import com.cruru.answer.domain.Answer;
import com.cruru.answer.dto.AnswerResponse;
import com.cruru.answer.service.AnswerService;
import com.cruru.applicant.controller.dto.ApplicantAnswerResponses;
import com.cruru.applicant.controller.dto.ApplicantBasicResponse;
import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.controller.dto.ApplicantResponse;
import com.cruru.applicant.controller.dto.ApplicantUpdateRequest;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.service.ApplicantService;
import com.cruru.process.controller.dto.ProcessSimpleResponse;
import com.cruru.process.domain.Process;
import com.cruru.process.service.ProcessService;
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
        List<Answer> answers = answerService.findAllByApplicant(applicant);
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
}
