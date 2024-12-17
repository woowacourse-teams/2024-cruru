package com.cruru.applicant.service;

import com.cruru.applicant.controller.request.EvaluationCreateRequest;
import com.cruru.applicant.controller.request.EvaluationUpdateRequest;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.Evaluation;
import com.cruru.applicant.domain.repository.EvaluationRepository;
import com.cruru.applicant.exception.EvaluationNotFoundException;
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

    public Evaluation findById(Long evaluationId) {
        return evaluationRepository.findById(evaluationId)
                .orElseThrow(EvaluationNotFoundException::new);
    }

    @Transactional
    public void create(EvaluationCreateRequest request, Process process, Applicant applicant) {
        evaluationRepository.save(new Evaluation(
                request.evaluator(),
                request.score(),
                request.content(),
                process,
                applicant
        ));
    }

    public List<Evaluation> findAllByProcessAndApplicant(Process process, Applicant applicant) {
        return evaluationRepository.findAllByProcessAndApplicant(process, applicant);
    }

    @Transactional
    public void update(EvaluationUpdateRequest request, Evaluation evaluation) {
        if (changeExists(request, evaluation)) {
            evaluationRepository.save(
                    new Evaluation(
                            evaluation.getId(),
                            request.evaluator(),
                            request.score(),
                            request.content(),
                            evaluation.getProcess(),
                            evaluation.getApplicant()
                    )
            );
        }
    }

    private boolean changeExists(EvaluationUpdateRequest request, Evaluation evaluation) {
        return !(
                evaluation.getContent().equals(request.content())
                        && evaluation.getScore().equals(request.score())
                        && evaluation.getEvaluator().equals(request.evaluator())
        );
    }

    @Transactional
    public void delete(long evaluationId) {
        evaluationRepository.deleteById(evaluationId);
    }

    @Transactional
    public void deleteByProcess(long processId) {
        evaluationRepository.deleteByProcessId(processId);
    }

    @Transactional
    public void deleteAllByProcesses(List<Process> processes) {
        evaluationRepository.deleteAllByProcesses(processes);
    }
}
