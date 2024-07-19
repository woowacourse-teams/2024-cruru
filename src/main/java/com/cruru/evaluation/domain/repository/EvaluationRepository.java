package com.cruru.evaluation.domain.repository;

import com.cruru.evaluation.domain.Evaluation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    List<Evaluation> findAllByProcessIdAndApplicantId(long processId, long applicantId);

}
