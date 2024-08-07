package com.cruru.evaluation.service;

import com.cruru.applicant.domain.Applicant;
import com.cruru.evaluation.controller.dto.EvaluationCreateRequest;
import com.cruru.evaluation.controller.dto.EvaluationUpdateRequest;
import com.cruru.evaluation.domain.Evaluation;
import com.cruru.evaluation.domain.repository.EvaluationRepository;
import com.cruru.evaluation.exception.EvaluationNotFoundException;
import com.cruru.evaluation.exception.badrequest.EvaluationNoChangeException;
import com.cruru.process.domain.Process;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;

    @Transactional
    public void create(EvaluationCreateRequest request, Process process, Applicant applicant) {
        evaluationRepository.save(new Evaluation(request.score(), request.content(), process, applicant));
    }

    public List<Evaluation> findAllByProcessAndApplicant(Process process, Applicant applicant) {
        return evaluationRepository.findAllByProcessAndApplicant(process, applicant);
    }

    @Transactional
    public void update(EvaluationUpdateRequest request, long evaluationId) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(EvaluationNotFoundException::new);

        if (evaluation.getContent().equals(request.content()) && evaluation.getScore().equals(request.score())) {
            throw new EvaluationNoChangeException();
        }

        evaluationRepository.save(
                new Evaluation(
                        evaluationId,
                        request.score(),
                        request.content(),
                        evaluation.getProcess(),
                        evaluation.getApplicant()
                )
        );
    }
}
