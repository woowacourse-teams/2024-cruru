package com.cruru.evaluation.service;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applicant.exception.ApplicantNotFoundException;
import com.cruru.evaluation.controller.dto.EvaluationCreateRequest;
import com.cruru.evaluation.domain.Evaluation;
import com.cruru.evaluation.domain.repository.EvaluationRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.process.exception.ProcessNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EvaluationService {

    private final ApplicantRepository applicantRepository;
    private final ProcessRepository processRepository;
    private final EvaluationRepository evaluationRepository;

    @Transactional
    public long create(EvaluationCreateRequest request, long processId, long applicantId) {
        Process process = processRepository.findById(processId)
                .orElseThrow(ProcessNotFoundException::new);

        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(ApplicantNotFoundException::new);

        Evaluation evaluation = evaluationRepository.save(
                new Evaluation(request.score(), request.content(), process, applicant)
        );

        return evaluation.getId();
    }
}
