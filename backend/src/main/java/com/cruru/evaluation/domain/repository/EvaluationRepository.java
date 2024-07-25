package com.cruru.evaluation.domain.repository;

import com.cruru.applicant.domain.Applicant;
import com.cruru.evaluation.domain.Evaluation;
import com.cruru.process.domain.Process;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    int countByApplicantAndProcess(Applicant applicant, Process process);

    List<Evaluation> findAllByProcessAndApplicant(Process process, Applicant applicant);
}
