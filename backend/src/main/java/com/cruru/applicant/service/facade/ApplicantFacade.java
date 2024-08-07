package com.cruru.applicant.service.facade;

import com.cruru.answer.domain.Answer;
import com.cruru.answer.service.AnswerService;
import com.cruru.applicant.controller.dto.ApplicantBasicResponse;
import com.cruru.applicant.controller.dto.ApplicantDetailResponse;
import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.controller.dto.ApplicantResponse;
import com.cruru.applicant.controller.dto.ApplicantUpdateRequest;
import com.cruru.applicant.controller.dto.QnaResponse;
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
        ApplicantResponse applicantResponse = new ApplicantResponse(applicant.getId(),
                applicant.getName(),
                applicant.getEmail(),
                applicant.getPhone(),
                applicant.getCreatedDate()
        );
        ProcessSimpleResponse processResponse = new ProcessSimpleResponse(applicant.getProcess().getId(),
                applicant.getProcess().getName()
        );
        return new ApplicantBasicResponse(applicantResponse, processResponse);
    }

    public ApplicantDetailResponse readDetailById(long id) {
        Applicant applicant = applicantService.findById(id);
        List<Answer> answers = answerService.findAllByApplicant(applicant);
        List<QnaResponse> qnaResponses = answerService.toQnaResponses(answers);
        return new ApplicantDetailResponse(qnaResponses);
    }

    @Transactional
    public void updateApplicantInformation(ApplicantUpdateRequest request, long applicantId) {
        Applicant applicant = applicantService.findById(applicantId);
        applicantService.updateApplicantInformation(request, applicant);
    }

    @Transactional
    public void updateApplicantProcess(long processId, ApplicantMoveRequest moveRequest) {
        Process process = processService.findById(processId);
        applicantService.moveApplicantProcess(process, moveRequest);
    }

    @Transactional
    public void updateApplicantToReject(long applicantId) {
        applicantService.reject(applicantId);
    }
}
